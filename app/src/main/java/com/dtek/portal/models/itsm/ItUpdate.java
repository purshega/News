package com.dtek.portal.models.itsm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//модель отмены, оценки, причины возврата, возврата в работу
public class ItUpdate {

    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    public static class Root {

        @SerializedName("id_order")
        @Expose
        private String idOrder; // id заявки
        @SerializedName("comment")
        @Expose
        private String comment; // комментарий заявки
        @SerializedName("rate")
        @Expose
        private String rate; // оценка заявки
        @SerializedName("loginid")
        @Expose
        private String loginId; // логин заявки


        public String getIdOrder() {
            return idOrder;
        }

        public void setIdOrder(String idOrder) {
            this.idOrder = idOrder;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }
    }
}
