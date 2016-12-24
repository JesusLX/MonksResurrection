package com.limox.jesus.monksresurrection.Fragments.PostView;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Singleton.Posts_Singleton;
import com.limox.jesus.monksresurrection.Singleton.Users_Singleton;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

public class PostView_Fragment extends Fragment {

    Post mPost;
    Toolbar mToolBar;
    ImageView mIvUserPicture;
    TextView mTxvUserName;
    TextView mTxvPostTitle;
    TextView mTxvPostDescription;
    private OnPostViewFragmentListener mCallback;

    Toolbar.OnMenuItemClickListener mListenerOnMenuClick;

    public interface OnPostViewFragmentListener{
        void startUserProfile(Bundle user);

    }

    public static PostView_Fragment newInstance(Bundle post) {

        PostView_Fragment fragment = new PostView_Fragment();
        fragment.setArguments(post);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPostViewFragmentListener)
            mCallback = (OnPostViewFragmentListener) context;
        else
            throw new ClassCastException(context.toString() +" must implement OnPostViewFragmentListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setHasOptionsMenu(true);
        mPost = getArguments().getParcelable(AllConstants.POST_PARCELABLE_KEY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_post_view, container,false);
        mToolBar = (Toolbar) rootView.findViewById(R.id.pv_tbTitleBar);
        mIvUserPicture = (ImageView) rootView.findViewById(R.id.pv_ivUserPicture);
        mTxvUserName = (TextView) rootView.findViewById(R.id.pv_txvUserName);
        mTxvPostTitle = (TextView) rootView.findViewById(R.id.pv_txvPostTitle);
        mTxvPostDescription = (TextView) rootView.findViewById(R.id.pv_txvPostDescription);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolBar.setOnMenuItemClickListener(mListenerOnMenuClick);
        fillWidgets();
        createOnOptionsItemSelected();
        mToolBar.inflateMenu(R.menu.menu_post_view);
        adapteMenu(mToolBar.getMenu());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.menu_post_view,menu);
    }

    /*
                            @Override
                            protected void onCreate(Bundle savedInstanceState) {
                                super.onCreate(savedInstanceState);
                                setContentView(R.layout.activity_post_view);
                                Bundle bundle = this.getIntent().getExtras();

                                this.mPost = Posts_Singleton.get().getPost(bundle.getInt("idPost"));
                                mToolBar = (Toolbar) findViewById(R.id.pv_tbTitleBar);
                                mToolBar.inflateMenu(R.menu.menu_post_view);
                                adapteMenu(mToolBar.getMenu());
                                CreateOnOptionsItemSelected();



                            }*/
    private void fillWidgets(){
        mIvUserPicture.setImageResource(Users_Singleton.get().getUserById(mPost.getIdUser()).getProfilePicture());
        mTxvUserName.setText(Users_Singleton.get().getUserById(mPost.getIdUser()).getName());
        mTxvPostTitle.setText(mPost.getTitle());
        mTxvPostDescription.setText(mPost.getDescription());
    }

    /**
     * Adapt the option menu for the current user
     * @param menu
     */
    private void adapteMenu(Menu menu){
        // getMenuInflater().inflate(R.menu.menu_post_view, menu);
         switch (Users_Singleton.get().getCurrentUser().getUserType()){
             case AllConstants.ADMIN_TYPE_ID://Is an admin

                 if (mPost.isPublicate())
                     menu.findItem(R.id.action_pv_ToPublished).setVisible(false);
                 if (mPost.isFixed()) {
                     menu.findItem(R.id.action_pv_ToFixes).setVisible(false);
                 }
                 break;

             case AllConstants.NORMALUSER_TYPE_ID: // In a current user

                 menu.findItem(R.id.action_pv_Edit).setVisible(false);
                 menu.findItem(R.id.action_pv_SendMessage).setVisible(false);
                 menu.findItem(R.id.action_pv_ToFixes).setVisible(false);
                 menu.findItem(R.id.action_pv_ToPublished).setVisible(false);
                 menu.findItem(R.id.action_pv_Delete).setVisible(false);
                 break;
         }
       // If the current user is the owner of the current post
       if (Users_Singleton.get().currentUserIsOwner(mPost)){
           if (!mPost.isFixed() && !Users_Singleton.get().getCurrentUser().isAdmin())
               menu.findItem(R.id.action_pv_Delete).setVisible(true);

           menu.findItem(R.id.action_pv_SendMessage).setVisible(false);
       }
     }


    /**
     * Create a listener for onMenuItemClick and add it to mListenerOnMenuClick
     */
    public void createOnOptionsItemSelected() {
        this.mListenerOnMenuClick = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_pv_ToFixes:
                        // Crea un cuadro de dialogo que pregunta si quiere hacer la acción y segun el ultimo parametro que le pases hace una acción u otra al darle al okay
                        createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_ToFixes, 0).show();
                        break;
                    case R.id.action_pv_ToPublished:
                        // Crea un cuadro de dialogo que pregunta si quiere hacer la acción y segun el ultimo parametro que le pases hace una acción u otra al darle al okay
                        createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_ToPublished, 1).show();
                        break;
            /*case R.id.action_SendMessage:
                // Todo Aqui meter para enviar mensaje
                break;*/
            /*case R.id.action_Edit:
                // Todo Aqui meter para editar
                break;*/
                    case R.id.action_pv_Delete:
                        // Crea un cuadro de dialogo que pregunta si quiere hacer la acción y segun el ultimo parametro que le pases hace una acción u otra al darle al okay
                        createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_Delete, 2).show();
                        break;
                }
                return true;

            }
        };
    }

    /**
     * Crea un cuadro de dialogo que hace una acción al darle al Ok
     * Dependiendo del typeAction hace
     * case 0:
     *  Envía la publicación a la lista de arreglados y cierra la activity
     *  case 1:
     *  Envía la publicación a la lista de publicados y cierra la activity
     *  case 2:
     *  Borra la publicación
     * @param idPost identificador de la publicacion
     * @param message Mensaje que se va a mostrar en el dialog
     * @param typeAction tipo de accion
     * @return
     */
    public AlertDialog createSimpleDialog(final int idPost, int message, final int typeAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.dialogAlertTitle)
                .setMessage(message)
                .setPositiveButton(R.string.btnOk,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (typeAction){
                                    case 0:
                                        Posts_Singleton.get().toFixPost(idPost);
                                        getActivity().onBackPressed();
                                        break;
                                    case 1:
                                        Posts_Singleton.get().toPublicPost(idPost);
                                        getActivity().onBackPressed();

                                        break;
                                    case 2:
                                        Posts_Singleton.get().deletePost(idPost);
                                        getActivity().onBackPressed();

                                        break;
                            }}
                        })
                .setNegativeButton(R.string.btnCancel,null);

        return builder.create();
    }
}
