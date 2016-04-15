package ar.rulosoft.custompref;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.TextView;

import ar.rulosoft.mimanganu.R;

/**
 * Created by Raul on 14/04/2016.
 */
public class SeekBarPreference extends DialogPreference {

    private int mFC;
    private int mMin;
    private int mMax;
    private int mValue;
    private String mSummary;
    private TextView textSummary;

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekBarPreference(Context context) {
        this(context, null);
    }

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomDialogPref, defStyleAttr, defStyleRes);
        mMin = a.getInteger(R.styleable.CustomDialogPref_val_min, 0);
        mMax = a.getInteger(R.styleable.CustomDialogPref_val_max, 9);
        mFC = -mMin;
        a.recycle();
        mSummary = (String) super.getSummary();
        setLayoutResource(R.layout.preference_seekbar_layout);
    }


    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return  (a.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);
        String getValue;
        if (restorePersistedValue) {
            if (defaultValue == null) {
                getValue = getPersistedString("0");
            } else {
                getValue = getPersistedString(String.valueOf(defaultValue));
            }
        } else {
            getValue = String.valueOf(defaultValue);
        }
        mValue = Integer.parseInt(getValue);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        SeekBar seekBar = (SeekBar) holder.findViewById(R.id.seekbar);
        textSummary = (TextView) holder.findViewById(android.R.id.summary);
        textSummary.setText(String.format(mSummary, mValue));
        seekBar.setMax(mMax + mFC);
        seekBar.setProgress(mValue + mFC);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mValue = progress - mFC;
                textSummary.setText(String.format(mSummary, mValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                persistString("" + mValue);
                notifyChanged();
            }
        });
        holder.itemView.setClickable(false);
    }
}