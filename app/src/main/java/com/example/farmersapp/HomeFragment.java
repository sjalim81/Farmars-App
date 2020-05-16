package com.example.farmersapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  private Button learnMore;
  private ImageView click_cultivation, click_microloan, click_poultry, click_disease, click_information, click_suggestion;
  RecyclerView recyclerView_stories;

  FragmentManager fragmentManager;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public HomeFragment() {
    // Required empty public constructor
  }

  public static HomeFragment newInstance(String param1, String param2) {
    HomeFragment fragment = new HomeFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View convertView = inflater.inflate(R.layout.fragment_home, container, false);

    click_cultivation = convertView.findViewById(R.id.click_cultivation);
    click_microloan = convertView.findViewById(R.id.click_microloan);
    click_disease = convertView.findViewById(R.id.click_disease);
    click_poultry = convertView.findViewById(R.id.click_poultry);
    click_suggestion = convertView.findViewById(R.id.click_suggestion);
    click_information = convertView.findViewById(R.id.click_information);
    learnMore = convertView.findViewById(R.id.learnMore);
    recyclerView_stories = convertView.findViewById(R.id.recyclerView_stories);

//    recyclerView_stories.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false ));


    click_information.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);

        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            Fragment fragment = new CultivationFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
          }
        },300);
      }
    });

    click_microloan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);

        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        },300);

      }
    });

    click_disease.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);

        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        },300);
      }
    });

    click_poultry.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        },300);

      }
    });

    click_cultivation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        },300);
      }
    });

    click_suggestion.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        },300);
      }
    });

    learnMore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      }
    });

    return convertView;
  }

  private void clickAnimation(View v) {
    Animation animShake = AnimationUtils.loadAnimation(getActivity(),R.anim.shake_button);
    v.startAnimation(animShake);
  }
}
