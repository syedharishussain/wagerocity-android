package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.OddHolder;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by haris on 10/05/15.
 */
public class BetSlipAdapter extends BaseAdapter {

    public static final String PARLAY = "parlay";
    public static final String TEASER = "teaser";

    DecimalFormat f = new DecimalFormat("####");

    public BetSlipAdapter(ArrayList<OddHolder> oddHolders, Context context) {
        this.oddHolders = new ArrayList<>(oddHolders);
        this.context = context;
        riskTextWatcherMap = new HashMap<>();
    }

    private ArrayList<OddHolder> oddHolders;
    private Context context;
    private Map<Integer, RiskTextWatcher> riskTextWatcherMap;
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

//        if (convertView == null) {
        Log.i("getView()", "Creating view holder for " + position);
        viewHolder = new ViewHolder();

//        if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.layout_cell_betslip, parent, false);

        viewHolder.teamName = (TextView) convertView.findViewById(R.id.textview_cell_betslip_team_name);
        viewHolder.betType = (TextView) convertView.findViewById(R.id.textview_cell_betslip_bet_type);
        viewHolder.betValue = (TextView) convertView.findViewById(R.id.textview_cell_betslip_bet_value);
        viewHolder.teamVsTeam = (TextView) convertView.findViewById(R.id.textview_cell_betslip_team_vs_team);

        viewHolder.risk = (EditText) convertView.findViewById(R.id.edittext_cell_betslip_risk);
        viewHolder.toWin = (EditText) convertView.findViewById(R.id.edittext_cell_betslip_to_win);

        viewHolder.cb = (CheckBox) convertView.findViewById(R.id.checkbox_betslip);

        viewHolder.spinner = (Spinner) convertView.findViewById(R.id.spinner_betslip);

        if (oddHolder.getBetTypeSPT().equals(TEASER)) {
            final ArrayList <String> arrayList = new ArrayList<>();
            if (oddHolder.getLeagueName().equals("nfl")) {
                arrayList.add("+6 pts " + oddHolder.getTeaser1());
                arrayList.add("+6.5 pts " + oddHolder.getTeaser2());
                arrayList.add("+7 pts " + oddHolder.getTeaser3());
            } else {
                arrayList.add("+4 pts " + oddHolder.getTeaser1());
                arrayList.add("+4.5 pts " + oddHolder.getTeaser2());
                arrayList.add("+5 pts " + oddHolder.getTeaser3());
            }



            ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.abc_simple_dropdown_hint, arrayList);
            viewHolder.spinner.setAdapter(arrayAdapter);

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            oddHolder.setOddValue(oddHolder.getTeaser1().toString());
                            break;
                        case 1:
                            oddHolder.setOddValue(oddHolder.getTeaser2().toString());
                            break;
                        case 2:
                            oddHolder.setOddValue(oddHolder.getTeaser3().toString());
                            break;
                    }

                    finalViewHolder.toWin.setText(String.valueOf(AndroidUtils.getToWinAmount(Float.parseFloat(oddHolder.getRiskValue()), Double.parseDouble(oddHolder.getOddValue()))));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        if ((oddHolder.getLeagueName().equals("nfl") || oddHolder.getLeagueName().equals("nba")) && oddHolder.getBetTypeSPT().equals(TEASER)) {
            viewHolder.spinner.setVisibility(View.VISIBLE);
        }

        viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                oddHolder.setIsChecked(isChecked);
            }
        });

        convertView.setTag(viewHolder);

//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }


        if (oddHolder != null) {

            viewHolder.teamName.setText(oddHolder.getTeamName());
            viewHolder.betType.setText(oddHolder.getBetTypeString());
            viewHolder.teamVsTeam.setText(oddHolder.getTeamVsteam());
            viewHolder.betValue.setText(oddHolder.getBetOT().equals("3") ? oddHolder.getPointSpreadString() : AndroidUtils.getSignedOddValue(oddHolder.getOddValue()));
            viewHolder.cb.setChecked(oddHolder.getIsChecked());
            viewHolder.risk.setText(f.format(Double.parseDouble(oddHolder.getRiskValue())));

            if (oddHolder.getBetTypeSPT().equals(PARLAY)) {
                viewHolder.toWin.setText(String.valueOf(f.format(oddHolder.getParlayValue() * Double.parseDouble(oddHolder.getRiskValue()))));
            }

            else if (oddHolder.getBetTypeSPT().equals(TEASER)) {
                viewHolder.toWin.setText(String.valueOf(f.format(AndroidUtils.getToWinAmount(Float.parseFloat(oddHolder.getRiskValue()), Double.parseDouble(oddHolder.getOddValue())))));
            }

            else {
                viewHolder.toWin.setText(String.valueOf(f.format(AndroidUtils.getToWinAmount(Float.parseFloat(oddHolder.getRiskValue()), Double.parseDouble(oddHolder.getOddValue())))));

            }


            RiskTextWatcher riskTextWatcher1;
            riskTextWatcher1 = new RiskTextWatcher(position, viewHolder);
            riskTextWatcherMap.put(position, riskTextWatcher1);
            viewHolder.risk.addTextChangedListener(riskTextWatcher1);

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
        CheckBox cb;
        Spinner spinner;
        Button button;
        TextView selectedTeaser;
    }

//    public interface OnBetSlipAdapterFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onBetSlipAdapterFragmentInteraction(Uri uri);
//    }

    private class RiskTextWatcher implements TextWatcher {

        private int position;
        private ViewHolder viewHolder;

        private RiskTextWatcher(int position, ViewHolder viewHolder) {
            this.position = position;
            this.viewHolder = viewHolder;
        }

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

            OddHolder oddHolder = oddHolders.get(position);

            if (string.length() > 0) {

                if (!viewHolder.risk.isFocused()) {
                    return;
                }

                if (oddHolder.getBetTypeSPT().equals(PARLAY)) {
                    Double value = Double.parseDouble(s.toString());

                    result = oddHolder.getParlayValue() * value;

                    viewHolder.toWin.setText(String.valueOf(f.format(result)));

                    oddHolder.setRiskValue(f.format(Double.parseDouble(s.toString())));

                    oddHolders.set(position, oddHolder);
                } else {

                    Double value = Double.parseDouble(s.toString());

                    result = AndroidUtils.getToWinAmount(value, Double.parseDouble(oddHolder.getOddValue()));

                    viewHolder.toWin.setText(String.valueOf(f.format(result)));

                    oddHolder.setRiskValue(f.format(Double.parseDouble(s.toString())));

                    oddHolders.set(position, oddHolder);

                }

            }
        }
    }
}
