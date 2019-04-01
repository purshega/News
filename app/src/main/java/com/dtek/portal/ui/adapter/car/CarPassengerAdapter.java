package com.dtek.portal.ui.adapter.car;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.callback.PassengerDiffCallback;
import com.dtek.portal.models.car.Employee;
import com.dtek.portal.models.car.Passenger;
import com.dtek.portal.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;

public class CarPassengerAdapter extends RecyclerView.Adapter<CarPassengerAdapter.ViewHolder> {
    private static final String TAG = "CarPassengerAdapter";

    private Context mContext;
    private List<Passenger> mPassengers = new ArrayList<>();
    private String strEntered; // введенная часть слова
    private List<Employee> mEmployeeList = new ArrayList<>(); // список полученный с сервера
    private List<Employee> filterList = new ArrayList<>(); // список фильтр


    // Конструктор
    public CarPassengerAdapter(Context context, List<Passenger> list) {
        mContext = context;
        this.mPassengers.addAll(list);
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public CarPassengerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_car_passenger, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Passenger passenger = mPassengers.get(position);
        Log.d(TAG, "bind, position = " + position);

        holder.tvNumbPassenger.setText(String.valueOf(passenger.getNumber()));

        holder.cbSmsPassenger.setChecked(passenger.isSms());
        holder.cbSmsPassenger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.etPhonePassenger.setVisibility(View.VISIBLE);
                    holder.etPhonePassenger.setText("+380");
                    passenger.setPhone("+380");
                } else {
                    holder.etPhonePassenger.setVisibility(View.GONE);
                    holder.etPhonePassenger.setText("");
                    passenger.setPhone("");
                }
                passenger.setSms(isChecked);
            }
        });

        holder.etPhonePassenger.setText(String.valueOf(passenger.getPhone()));
        if (passenger.isSms()) {
            holder.etPhonePassenger.setVisibility(View.VISIBLE);
        }
        holder.etPhonePassenger.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passenger.setPhone(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        holder.etCommentPassenger.setText(String.valueOf(passenger.getComment()));
        holder.etCommentPassenger.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passenger.setComment(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        final ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(mContext,
                R.array.car_passenger, android.R.layout.simple_spinner_item);
        final String strType = passenger.getType();
        int posType = adapterType.getPosition(strType);
        holder.spinnerTypePassenger.setSelection(posType);
        holder.spinnerTypePassenger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                String[] spinnerType = mContext.getResources().getStringArray(R.array.car_passenger);
                passenger.setType(spinnerType[selectedItemPosition]);
//                Toast.makeText(mContext,
//                        "Ваш выбор: " + spinnerType[selectedItemPosition], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        holder.actvFioPassenger.setText(String.valueOf(passenger.getFio()));
        holder.actvFioPassenger.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                strEntered = charSequence.toString(); // ввод часть слова

                if (passenger.getType().equals("Сотрудник")) {
                    if (strEntered.length() > 2) { //количество введенных символов
                        if (mEmployeeList.isEmpty()) { // если массив фильтра пустой
                            loadData(strEntered); // загружаем данные и присваиваем их массиву mEmployeeList по введенному слову
                        }
                    } else {
                        mEmployeeList.clear(); // очищаем массив полученный из сервера
                    }


                    filterList.clear(); // очищаем массив фильтра

                    for (Employee employee : mEmployeeList) { // перебераем массив данных из сервера
                        if (employee.getFio().toLowerCase().contains(strEntered.toLowerCase())) { //проверяем иммя на содержание нашего текста
                            filterList.add(employee); // добавляем объект в массив фильта
                        }
                    }

                    Log.i(TAG, "mEmployeeList: " + mEmployeeList.size());
                    Log.i(TAG, "filterList: " + filterList.size());
                    if (filterList.isEmpty()) { // если массив фильтра не пустой
                        getDropList(mEmployeeList); // выпадающий список с масивом полученным из сервера
                    } else {
                        getDropList(filterList); // выпадающий список с масивом фильтра
                    }
                } else {
                    mEmployeeList.clear();
                    filterList.clear();
                    passenger.setFio(strEntered);
                    passenger.setLogin("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            public void getDropList(List<Employee> list) {
                CarEmployeeAdapter adapter = new CarEmployeeAdapter(mContext, R.layout.item_employee, list);
                holder.actvFioPassenger.setThreshold(1);//will start working from first characte
                holder.actvFioPassenger.setAdapter(adapter);//setting the adapter data into the//
                holder.actvFioPassenger.showDropDown();
            }
        });
        holder.actvFioPassenger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (passenger.getType().equals("Сотрудник")) {
                    Employee employee = filterList.get(0); // индекс 0, потому что при выборе он становится 1 в списке
                    String login = employee.getLogin();
                    passenger.setFio(employee.getFio());
                    passenger.setLogin(employee.getLogin());
                    Toast.makeText(mContext, "выбран login - " + login, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadData(String str) {
        Toast.makeText(mContext, "Обращаемся за списком сотрудников к серверу", Toast.LENGTH_SHORT).show();
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        Employee employee = new Employee();
        employee.setFio(str);

        RestManager.getApi()
                .sendEmployee(map, employee)
                .enqueue(new Callback<List<Employee>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Employee>> call, @NonNull Response<List<Employee>> response) {
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful() && response.body() != null) {
                            mEmployeeList = response.body();
                            if (mEmployeeList != null) {
                                Log.i(TAG, "mEmployeeList: " + mEmployeeList.size());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Employee>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mPassengers == null ? 0 : mPassengers.size();
    }

    public void addItem(Passenger passenger) {
        Log.i(TAG, "addItem: " + mPassengers.size());
        mPassengers.add(passenger);
        notifyItemInserted(mPassengers.size());
        Log.i(TAG, "addItem: " + mPassengers.size());
    }

    public void removeItem(int position) {
        mPassengers.remove(position);
//        Log.i(TAG, "onRemoveClick: " + mPassengers.size());
        //обновляем порядковые номера элементов
        int countNumb = position + 1;
        for (int i = position; i < mPassengers.size(); i++) { // пробнгаемся по порядковым номерам после удаленного элемента
            mPassengers.get(i).setNumber(countNumb++); // изменяем порядковые номера
        }
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mPassengers.size());
    }

    public void updateListFragment(List<Passenger> list) {
        list.clear();
        list.addAll(mPassengers);
    }

    public void updatePassengerListItems(List<Passenger> passengers) {
        final PassengerDiffCallback diffCallback = new PassengerDiffCallback(this.mPassengers, passengers);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.mPassengers.clear();
        this.mPassengers.addAll(passengers);
        diffResult.dispatchUpdatesTo(this);
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.iv_remove)
        ImageView ivRemove;
        @BindView(R.id.tv_numb_passenger)
        TextView tvNumbPassenger;
        @BindView(R.id.spinner_type_passenger)
        Spinner spinnerTypePassenger;
        @BindView(R.id.actv_fio_passenger)
        AutoCompleteTextView actvFioPassenger;
        @BindView(R.id.cb_sms_passenger)
        CheckBox cbSmsPassenger;
        @BindView(R.id.et_phone_passenger)
        EditText etPhonePassenger;
        @BindView(R.id.et_comment_passenger)
        EditText etCommentPassenger;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick(R.id.iv_remove)
        void onRemoveClick(View v) {
            switch (v.getId()) {
                case R.id.iv_remove:
                    if (sPassengerListener != null) {
                        sPassengerListener.onRemovePassenger(getAdapterPosition());
                    }
                    break;
            }
        }
    }

    //интерфейс
    private static PassengerListener sPassengerListener; //переменная

    public void setPassengerListener(PassengerListener listener) {//кот. содержит в себе объект
        CarPassengerAdapter.sPassengerListener = listener;
    }

    public interface PassengerListener { //этот объект реализует этот интерфейс
        void onRemovePassenger(int adapterPosition);
    }
}
