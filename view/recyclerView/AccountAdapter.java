package karimi.javad.accounting.view.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import karimi.javad.accounting.AccountViewModel;
import karimi.javad.accounting.R;
import karimi.javad.accounting.db.entity.Operation;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {

    public final static String BES = "بس";
    public final static String BED = "بد";
    private final String account;
//    private BigInteger remine;
    private int remine;

    private List<Operation> operationList;
    private final LayoutInflater mInflater;

    public AccountAdapter(Context context, String account) {
        mInflater = LayoutInflater.from(context);
        this.account = account;
//        remine = new BigInteger("0");
        remine = 0;
    }

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.total_notebook_account_item, viewGroup, false);
        return new AccountHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder accountHolder, int i) {
        if(operationList != null){
            Operation operation = operationList.get(i);
            accountHolder.txtDate.setText(operation.getMonth() + " / " + operation.getDay());
            if(operation.getDebtor().equals(account)) {
                accountHolder.txtMoney1.setText(operation.getMoney());
//                accountHolder.txtMoney2.setVisibility(View.INVISIBLE);
                accountHolder.txtMoney2.setText("");
//                remine.add(BigInteger.valueOf(Integer.valueOf(operation.getMoney())));
                remine += Integer.valueOf(operation.getMoney());
            }
            else {
                accountHolder.txtMoney2.setText(operation.getMoney());
//                accountHolder.txtMoney1.setVisibility(View.INVISIBLE);
                accountHolder.txtMoney1.setText("");
//                remine.add(BigInteger.valueOf(-Integer.valueOf(operation.getMoney())));
                remine -= Integer.valueOf(operation.getMoney());
            }
            accountHolder.txtDesc.setText(operation.getDescription());
//            accountHolder.txtRemind.setText(remine.abs().toString());
            accountHolder.txtRemind.setText(Integer.toString(Math.abs(remine)));
        }
        else{
            accountHolder.txtDesc.setText("An error occurred!");
        }
    }

    @Override
    public int getItemCount() {
        if(operationList == null)
            return 0;
        else
            return operationList.size();
    }

    public List<Operation> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<Operation> operationList) {
        this.operationList = operationList;
        Collections.sort(operationList);
        notifyDataSetChanged();
    }

    public int getRemine() {
        return remine;
    }

    public void setRemine(int remine) {
        this.remine = remine;
    }

    class AccountHolder extends RecyclerView.ViewHolder{

        private final TextView txtDate, txtMoney1, txtMoney2, txtDesc, txtRemind;


        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            txtRemind = itemView.findViewById(R.id.txt_remine);
            txtDate = itemView.findViewById(R.id.txt_date2);
            txtMoney1 = itemView.findViewById(R.id.txt_money3);
            txtMoney2 = itemView.findViewById(R.id.txt_money4);
            txtDesc = itemView.findViewById(R.id.txt_desc2);
        }
    }
}
