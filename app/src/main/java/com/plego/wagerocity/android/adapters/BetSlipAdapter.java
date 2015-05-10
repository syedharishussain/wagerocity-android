package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.OddHolder;
import com.plego.wagerocity.utils.AndroidUtils;

import java.util.ArrayList;

/**
 * Created by haris on 10/05/15.
 */
public class BetSlipAdapter extends BaseAdapter {

    public BetSlipAdapter(ArrayList<OddHolder> oddHolders, Context context) {
        this.oddHolders = oddHolders;
        this.context = context;
    }

    private TextWatcher riskAmountChange;
    private ArrayList <OddHolder> oddHolders;
    private Context context;
//    private BetOnGameFragment.OnBetOnGameFragmentInteractionListener mListener;

    @Override
    public int getCount() {
        return oddHolders.size();
    }

    @Override
    public OddHolder getItem(int position) {
        return this.oddHolders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        final OddHolder oddHolder = this.oddHolders.get(position);

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_cell_betslip, parent, false);

            viewHolder.teamName = (TextView) convertView.findViewById(R.id.textview_cell_betslip_team_name);
            viewHolder.betType = (TextView) convertView.findViewById(R.id.textview_cell_betslip_bet_type);
            viewHolder.betValue = (TextView) convertView.findViewById(R.id.textview_cell_betslip_bet_value);
            viewHolder.teamVsTeam = (TextView) convertView.findViewById(R.id.textview_cell_betslip_team_vs_team);

            viewHolder.risk = (EditText) convertView.findViewById(R.id.edittext_cell_betslip_risk);
            viewHolder.toWin = (EditText) convertView.findViewById(R.id.edittext_cell_betslip_to_win);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (oddHolder != null) {
            viewHolder.teamName.setText(oddHolder.getTeamName());
            viewHolder.betType.setText(oddHolder.getBetTypeString());
            viewHolder.betValue.setText( oddHolder.getBetOT().equals("3") ? oddHolder.getPointSpreadString() : oddHolder.getOddValue() );
            viewHolder.teamVsTeam.setText(oddHolder.getTeamVsteam());

            final ViewHolder finalViewHolder = viewHolder;



            riskAmountChange = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double result = 0.0;
                    String string = s.toString();

                    if (string.length() > 0) {

                        finalViewHolder.risk.removeTextChangedListener(riskAmountChange);

                        Double value = Double.parseDouble(s.toString());

                        result = AndroidUtils.getToWinAmount(value, Double.parseDouble(oddHolder.getOddValue()));

                        finalViewHolder.toWin.setText(String.valueOf(result));

                        finalViewHolder.risk.addTextChangedListener(riskAmountChange);
                    }
                }
            };

            viewHolder.risk.addTextChangedListener(riskAmountChange);

        }

        return convertView;
    }

    class ViewHolder {
        TextView teamName;
        TextView betType;
        TextView betValue;
        TextView teamVsTeam;
        EditText risk;
        EditText toWin;
    }

//    public interface OnBetSlipAdapterFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onBetSlipAdapterFragmentInteraction(Uri uri);
//    }
}
