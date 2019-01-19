package karimi.javad.accounting.view.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import karimi.javad.accounting.R;
import karimi.javad.accounting.db.entity.Operation;

public class NewspaperNotebookAdapter extends RecyclerView.Adapter<NewspaperNotebookAdapter.NewspaperNotebookHolder> {

    private final LayoutInflater mInflater;
    private List<Operation> operationList;

    public NewspaperNotebookAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NewspaperNotebookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.newspaper_item, viewGroup, false);
        return new NewspaperNotebookHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NewspaperNotebookHolder newspaperNotebookHolder, int i) {
        if(operationList != null){
            Operation operation = operationList.get(i);
            newspaperNotebookHolder.txtRow.setText(Integer.toString(i + 1));
            newspaperNotebookHolder.txtDate.setText(operation.getMonth() + " / " + operation.getDay());
            newspaperNotebookHolder.txtDebtor.setText(operation.getDebtor());
            newspaperNotebookHolder.txtCreditor.setText(operation.getCreditor());
            newspaperNotebookHolder.txtMoney1.setText(operation.getMoney());
            newspaperNotebookHolder.txtMoney2.setText(operation.getMoney());
            newspaperNotebookHolder.txtDesc.setText(operation.getDescription());
        }
        else{
            newspaperNotebookHolder.txtDesc.setText("An error occurred!");
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
        Collections.sort(operationList);
        notifyDataSetChanged();
    }

    class NewspaperNotebookHolder extends RecyclerView.ViewHolder{

        private final TextView txtRow, txtDate, txtDebtor, txtCreditor, txtMoney1, txtMoney2, txtDesc;

        public NewspaperNotebookHolder(@NonNull View itemView) {
            super(itemView);
            txtRow = itemView.findViewById(R.id.txt_row1);
            txtDate = itemView.findViewById(R.id.txt_date1);
            txtDebtor = itemView.findViewById(R.id.txt_debtor1);
            txtCreditor = itemView.findViewById(R.id.txt_creditor1);
            txtMoney1 = itemView.findViewById(R.id.txt_money1);
            txtMoney2 = itemView.findViewById(R.id.txt_money2);
            txtDesc = itemView.findViewById(R.id.txt_desc1);
        }
    }
}
