package com.example.adrianpc.s236308_mappe_2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.adrianpc.s236308_mappe_2.R;
import com.example.adrianpc.s236308_mappe_2.activities.MainActivity;

public class MenuFragment extends Fragment {

    public static final int EXIT_ID = 1000;
    public static final int ACTIVATE_DELETE_ID = 1001;
    public static final int DEACTIVATE_DELETE_ID = 1002;
    public static final int ADD_ID = 1003;
    public static final int OPEN_SETTINGS = 1004;
    public static final int SAVE = 1005;
    public static final int SELECT_IMAGE = 1006;
    public static final int MENU_FOR_MAIN = 100;
    public static final int MENU_FOR_CREATE = 101;
    public static final int MENU_FOR_CONTACT = 102;
    public static final int MENU_FOR_EDIT = 103;

    private ImageButton first_left, second_left, third_left, fourth_left;
    private View view;
    private int activeMenu;

    private MenuListener listener;

    public MenuFragment(MenuListener listener, int activeMenu) {
        this.listener = listener;
        this.activeMenu = activeMenu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        ((ImageButton)view.findViewById(R.id.menu_exit_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onInteraction(EXIT_ID);
            }
        });
        first_left = (ImageButton) view.findViewById(R.id.menubutton_first_from_left);
        second_left = (ImageButton) view.findViewById(R.id.menubutton_second_from_left);
        third_left = (ImageButton) view.findViewById(R.id.menubutton_third_from_left);
        fourth_left = (ImageButton) view.findViewById(R.id.menubutton_fourth_from_left);

        selectMenu(activeMenu);
        return view;
    }

    public void selectMenu(int menuType) {
        activeMenu = menuType;
        switch (menuType) {
            case MENU_FOR_MAIN: {
                setMainMenu();
                break;
            }case MENU_FOR_CREATE: {
                setEditOrCreateMenu(true);
                break;
            }case MENU_FOR_EDIT: {
                setEditOrCreateMenu(true);
                break;
            }case  MENU_FOR_CONTACT :{
                setEditOrCreateMenu(false);
                break;
            }
        }
    }

    private void addListener(View view, final int id) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onInteraction(id);
            }
        });
    }

    public void setEditOrCreateMenu(boolean image) {
        clearMenu();
        fourth_left.setImageResource((image)?android.R.drawable.ic_menu_save:android.R.drawable.ic_menu_edit);
        fourth_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onInteraction(SAVE);
            }
        });
        if(image) {
            third_left.setImageResource(android.R.drawable.ic_menu_camera);
            third_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onInteraction(SELECT_IMAGE);
                }
            });
        }
    }

    private void clearMenu() {
        first_left.setOnClickListener(null);
        second_left.setOnClickListener(null);
        third_left.setOnClickListener(null);
        fourth_left.setOnClickListener(null);
        first_left.setImageResource(0);
        second_left.setImageResource(0);
        third_left.setImageResource(0);
        fourth_left.setImageResource(0);
    }

    public void setMainMenu() {
        clearMenu();
        if(listener instanceof MainActivity) {
            second_left.setImageResource(R.mipmap.ic_cogwheel);
            addListener(second_left, OPEN_SETTINGS);
            third_left.setImageResource(android.R.drawable.ic_input_add);
            addListener(third_left, ADD_ID);
            fourth_left.setImageResource(R.mipmap.list_edit_icon);
            addListener(fourth_left, ACTIVATE_DELETE_ID);
        } else {
        }
    }

    public void setDeleteMenu() {
        clearMenu();
        fourth_left.setImageResource(android.R.drawable.ic_menu_save);
        addListener(fourth_left, DEACTIVATE_DELETE_ID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface MenuListener {
        void onInteraction(int interactionID);
    }
}
