package karimi.javad.accounting.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "OperationTable")
public class Operation implements Comparable<Operation>{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int day;
    private int month;
    private String debtor;
    private String creditor;
    private String description;
    private String money;

    public Operation(int day, int month, String debtor, String creditor, String description, String money) {
        this.day = day;
        this.month = month;
        this.debtor = debtor;
        this.creditor = creditor;
        this.description = description;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    @Override
    public int compareTo(@NonNull Operation operation) {
        int value = 0;
        if(operation.getMonth() < 7)
            value += (operation.getMonth() - 1) * 31;
        else
            value += (operation.getMonth() - 1) * 30;
        value += operation.getDay();

        int value1 = 0;
        if(month < 7)
            value1 += (month - 1) * 31;
        else
            value1 += (month - 1) * 30;
        value1 += day;
        return value1 - value;
    }
}
