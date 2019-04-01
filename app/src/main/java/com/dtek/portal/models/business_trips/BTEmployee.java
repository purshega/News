package com.dtek.portal.models.business_trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BTEmployee {

    @SerializedName("personnelNumber")
    @Expose
    private Long personnelNumber;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("surname")
    @Expose
    private String surname;

    @SerializedName("patronymic")
    @Expose
    private String patronomic;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("department")
    @Expose
    private String department;

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("companyId")
    @Expose
    private String companyId;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    public Long getPersonnelNumber() {
        return personnelNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronomic() {
        return patronomic;
    }

    public String getCompany() {
        return company;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public String getCategory() {
        return category;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "BTEmployee{" +
                "personnelNumber=" + personnelNumber +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronomic='" + patronomic + '\'' +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", category='" + category + '\'' +
                ", companyId='" + companyId + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
