package com.example.tindernet.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tindernet.KolodaSampleAdapter;
import com.example.tindernet.R;
import com.example.tindernet.databinding.FragmentHomeBinding;
import com.yalantis.library.KolodaListener;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private KolodaSampleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initializeDeck();
        fillData();
        setUpClickListeners();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.activityMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    binding.activityMain.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    binding.activityMain.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    private void initializeDeck() {
        binding.koloda.setKolodaListener(new KolodaListener() {

            private int cardsSwiped = 0;

            @Override
            public void onNewTopCard(int position) {
                Toast.makeText(getContext(), "New top card: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCardSwipedLeft(int position) {
                Toast.makeText(getContext(), "Card swiped left: " + position, Toast.LENGTH_SHORT).show();
                cardsSwiped++;
            }

            @Override
            public void onCardSwipedRight(int position) {
                Toast.makeText(getContext(), "Card swiped right: " + position, Toast.LENGTH_SHORT).show();
                cardsSwiped++;
            }

            @Override
            public void onEmptyDeck() {
                Toast.makeText(getContext(), "Empty deck!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCardDrag(int position, @NonNull View cardView, float progress) {
                // Implementa tu lógica si necesitas hacer algo mientras se arrastra la tarjeta
            }

            @Override
            public void onClickRight(int position) {
                Toast.makeText(getContext(), "Card clicked right: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickLeft(int position) {
                Toast.makeText(getContext(), "Card clicked left: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCardSingleTap(int position) {
                Toast.makeText(getContext(), "Card single tap: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCardLongPress(int position) {
                Toast.makeText(getContext(), "Card long press: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCardDoubleTap(int position) {
                Toast.makeText(getContext(), "Card double tap: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillData() {
        Integer[] data = {
                R.drawable.empresa1,
                R.drawable.empresa2,
        };
        adapter = new KolodaSampleAdapter(getContext(), java.util.Arrays.asList(data));
        binding.koloda.setAdapter(adapter);
        binding.koloda.setNeedCircleLoading(true);
    }

    private void setUpClickListeners() {
        binding.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.koloda.onClickLeft();
            }
        });
        binding.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.koloda.onClickRight();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.actionReload) {
            binding.koloda.reloadAdapterData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}