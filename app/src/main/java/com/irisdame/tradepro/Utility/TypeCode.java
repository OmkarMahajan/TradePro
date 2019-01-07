package com.irisdame.tradepro.Utility;

import android.content.Context;
import android.content.SharedPreferences;

public class TypeCode {

    public static Context context;

    public TypeCode(Context context) {
        this.context = context;
    }

    public static int PRODUCT_LIST_WITH_STOCK = 0;
    public static int QUANTITY_WISE_SALES_AND_PURCHASE_REPORT = 1;
    public static int CUSTOMER_LIST_AND_BALANCE = 2;
    public static int SUPPLIER_LIST_AND_BALANCE = 3;
    public static int PAYMENT_REPORT = 4;
    public static int EXPENSE_REPORT = 5;

    public static String[][] copyData;

    public static String[] getTypes() {
        return new String[] {
                "PRODUCT_LIST_WITH_STOCK", "QUANTITY_WISE_SALES_AND_PURCHASE_REPORT", "CUSTOMER_LIST_AND_BALANCE",
                "SUPPLIER_LIST_AND_BALANCE", "PAYMENT_REPORT", "EXPENSE_REPORT"
        };
    }

    public static String[] getFieldsList(int type) {
        switch (type) {
            case 0:
                return new String[] {
                        "Product_Name", "Purchase_Price", "Sales_Price", "Safety_Stock",
                        "Available_Stock", "Tax_Name"
                };

            case 1:
                return new String[] {
                        "Product_Name", "Quantity", "Purchase_Total", "Sell_Total"
                };

            case 2:
                return new String[] {
                        "Customer_Name", "Phone_Number", "Balance_Payment", "Credit_Limit"
                };

            case 3:
                return new String[] {
                        "Supplier_Name", "Phone_Number", "Balance_Dues", "Credit_Limit"
                };

            case 4:
                return new String[] {
                        "Date", "Report_Number", "Payment_Number", "Amount", "Type"
                };

            case 5:
                return new String[] {
                        "Daily_Incoming_Payment", "Daily_Outgoing_Payment", "Other_Expenses"
                };

            default:
                return null;
        }
    }

    public static int returnSizeOfType(int type) {
        switch (type) {
            case 0:
                return 6;

            case 1:
                return 4;

            case 2:
                return 4;

            case 3:
                return 4;

            case 4:
                return 5;

            case 5:
                return 3;

            default:
                return 0;
        }
    }

    public static int noOfActivity(int type){
        switch (type) {
            case 1:
                return 2;

            default:
                 return 1;
        }
    }


    public static String returnJSONUrl(int type) {
        switch (type) {
            case 0:
                return "http://traderepotest.azurewebsites.net/api/repo/Tradepro_test/query/0";
                //return "https://api.myjson.com/bins/17npzg";

            case 1:
                return "http://traderepotest.azurewebsites.net/api/repo/Tradepro_test/query/1";
                //return "https://api.myjson.com/bins/1fblb8";

            case 2:
                return "http://traderepotest.azurewebsites.net/api/repo/Tradepro_test/query/2";
                //return "https://api.myjson.com/bins/i1glg";

            case 3:
                return "http://traderepotest.azurewebsites.net/api/repo/Tradepro_test/query/3";
                //return "https://api.myjson.com/bins/bhoxw";

            case 4:
                return "http://traderepotest.azurewebsites.net/api/repo/Tradepro_test/query/4";
                //return "https://api.myjson.com/bins/wmkx0";

            case 5:
                return "http://traderepotest.azurewebsites.net/api/repo/Tradepro_test/query/5";
                //return "https://api.myjson.com/bins/m5co4";

            default:
                return "www.irisdame.com";
        }
    }


}
