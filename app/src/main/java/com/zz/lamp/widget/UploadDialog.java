package com.zz.lamp.widget;/**
 * Created by ${sj} on 2016/11/11.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zz.lamp.R;
import com.zz.lamp.bean.Version;


/**
 * Created by chengxu03 on 2016/11/11.
 */
public class UploadDialog extends Dialog
{

    public UploadDialog(Context context, int theme)
    {
        super(context, theme);
    }

    public UploadDialog(Context context)
    {
        super(context);
    }

    public static class Builder
    {
        private Context context;
        private String title;
        private String message;
        private Version version;
//        private boolean isforce;
        private String positiveButtonText;
        private OnClickListener positiveButtonClickListener;


        public Builder(Context context)
        {
            this.context = context;
        }

        public Builder(Context context, String title, String msg, Version version)
        {
            this.context = context;
            this.message = msg;
            this.title = title;
            this.version = version;
        }

        public Builder setMessage(String message)
        {
            this.message = message;
            return this;
        }
        public Builder setMessage(int message)
        {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setTitle(int title)
        {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setGone(int title)
        {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title)
        {
            this.title = title;
            return this;
        }
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener)
        {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener)
        {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }


//        public void setTouchDismiss(boolean isForce) {
//           this.isforce = isForce;
//        }

        public CustomDialog create()
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context, R.style.custom_Progress);
            View layout = inflater.inflate(R.layout.dialog_one_buttom, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            ((TextView) layout.findViewById(R.id.lblDialogMessage)).setText(Html.fromHtml("<span style=\"color:white\"><ul>"+version.getChanges()+"</span>"));
            String s = message +"→"+version.getVersion_name();
            ((TextView) layout.findViewById(R.id.lblDialogSubTitle)).setText(s);
            View imageClose = layout.findViewById(R.id.imageClose);
            imageClose.setVisibility(version.getNecessary() == 0?View.VISIBLE:View.GONE);
            imageClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if (positiveButtonText != null)
            {
                ((TextView) layout.findViewById(R.id.lblPositive)).setText(positiveButtonText);
                if (positiveButtonClickListener != null)
                {
                    ((TextView) layout.findViewById(R.id.lblPositive)).setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            }
            else
            {
                layout.findViewById(R.id.lblPositive).setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }

}
