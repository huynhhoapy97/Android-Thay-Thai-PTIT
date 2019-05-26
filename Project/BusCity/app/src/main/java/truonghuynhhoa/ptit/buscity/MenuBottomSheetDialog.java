package truonghuynhhoa.ptit.buscity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import truonghuynhhoa.ptit.adapter.MenuAdapter;
import truonghuynhhoa.ptit.model.Menu;


public class MenuBottomSheetDialog extends BottomSheetDialogFragment {

    private ListView lvMenu;
    private MenuAdapter menuAdapter;
    private Activity activity;
    public String language;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = MenuBottomSheetDialog.this.getLayoutInflater();
        View menuBottomSheetDialog = layoutInflater.inflate(R.layout.activity_menu, null);

        lvMenu = menuBottomSheetDialog.findViewById(R.id.lvMenu);
        menuAdapter = new MenuAdapter(activity, R.layout.item_menu);

        lvMenu.setAdapter(menuAdapter);

        language = getLanguage();

        if(language.equals("en") || language.equals("")){
            menuAdapter.add(new Menu(R.drawable.cloud, "Update data"));
            menuAdapter.add(new Menu(R.drawable.language, "Choose language"));
            menuAdapter.add(new Menu(R.drawable.detail, "View information"));
        }
        else if(language.equals("vi")){
            menuAdapter.add(new Menu(R.drawable.cloud, "Cập nhật dữ liệu"));
            menuAdapter.add(new Menu(R.drawable.language, "Chọn ngôn ngữ"));
            menuAdapter.add(new Menu(R.drawable.detail, "Thông tin ứng dụng"));
        }


        addEvents();

        return menuBottomSheetDialog;
    }

    public void addEvents(){
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    dismiss();

                    Intent intent = new Intent(activity, DataActivity.class);
                    startActivity(intent);
                }
                else if(position == 1){
                    dismiss();

                    Intent intent = new Intent(activity, LanguageActivity.class);
                    startActivity(intent);
                }
                else if(position == 2){
                    dismiss();

                    Intent intent = new Intent(activity, InformationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
