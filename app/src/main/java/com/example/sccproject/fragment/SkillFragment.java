package com.example.sccproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.sccproject.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SkillFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.skill_shop,container,false);
        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(),SweetAlertDialog.NORMAL_TYPE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitleText("技能");
        ImageButton k1 = (ImageButton)view.findViewById(R.id.k1);
        ImageButton k2 = (ImageButton)view.findViewById(R.id.k2);
        ImageButton k3 = (ImageButton)view.findViewById(R.id.k3);
        ImageButton k4 = (ImageButton)view.findViewById(R.id.k4);
        ImageButton k5 = (ImageButton)view.findViewById(R.id.k5);
        ImageButton k6 = (ImageButton)view.findViewById(R.id.k6);
        ImageButton k7 = (ImageButton)view.findViewById(R.id.k7);
        ImageButton k8 = (ImageButton)view.findViewById(R.id.k8);
        ImageButton kk = (ImageButton)view.findViewById(R.id.kk);
        k1.setOnClickListener(v->{
            dialog.setTitleText("技能\n"+R.string.k1);
            dialog.show();
        });
        k2.setOnClickListener(v->{
            dialog.setTitleText("技能\n"+R.string.k2);
            dialog.show();
        });
        k3.setOnClickListener(v->{
            dialog.setTitleText("技能\n"+R.string.k3);
            dialog.show();
        });
        k4.setOnClickListener(v->{
            dialog.setTitleText("技能\n"+R.string.k4);
            dialog.show();
        });
        k5.setOnClickListener(v->{
            dialog.setTitleText("技能\n"+R.string.k5);
            dialog.show();
        });
        k6.setOnClickListener(v->{
            dialog.setTitleText("技能\n"+R.string.k6);
            dialog.show();
        });
        k7.setOnClickListener(v->{
            dialog.setTitleText("技能\n"+R.string.k7);
            dialog.show();
        });
        k8.setOnClickListener(v->{
            dialog.setTitleText("技能\n"+R.string.k8);
            dialog.show();
        });
        kk.setOnClickListener(v->{
            dialog.setTitleText("技能\n"+R.string.kk);
            dialog.show();
        });



        return view;
    }
}
