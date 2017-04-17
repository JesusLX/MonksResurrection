package com.limox.jesus.teambeta.Fragments.PostView;

import android.app.Activity;
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

import com.limox.jesus.teambeta.Interfaces.PostViewPresenter;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.PostViewPresenterImpl;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;

import java.util.Date;

import it.sephiroth.android.library.picasso.Picasso;

public class PostView_Fragment extends Fragment implements PostViewPresenter.View, UserManagerPresenter.View{

    private User mUser;
    Post mPost;
    Toolbar mToolBar;
    ImageView mIvUserPicture;
    ImageView mIvLike;
    ImageView mIvDislike;
    ImageView mIvComments;
    TextView mTxvUserName;
    TextView mTxvPostTitle;
    TextView mTxvPostDescription;
    TextView mTxvPostScore;
    private PostViewPresenter mPresenter;
    private UserManagerPresenter mUserPresenter;
    private OnPostViewFragmentListener mCallback;

    Toolbar.OnMenuItemClickListener mListenerOnMenuClick;

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onUserCreated() {

    }

    @Override
    public void onUserObtained(User tryUser) {
        mUser = tryUser;
        fillWidgets();

    }

    public interface OnPostViewFragmentListener{
        void startUserProfile(Bundle user);
        void showPostComments();
    }

    public static PostView_Fragment newInstance(Bundle post) {

        PostView_Fragment fragment = new PostView_Fragment();
        fragment.setArguments(post);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnPostViewFragmentListener)
            mCallback = (OnPostViewFragmentListener) activity;
        else
            throw new ClassCastException(activity.toString() +" must implement OnPostViewFragmentListener");
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
        mPresenter = new PostViewPresenterImpl(this);
        mPost = getArguments().getParcelable(AllConstants.Keys.Parcelables.POST_PARCELABLE_KEY);
        mPost.setCreationDate(new Date(getArguments().getLong(AllConstants.Keys.Parcelables.POST_CREATION_DATE)));
        mUserPresenter = new UserManagerPresenterImpl(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStart() {
        super.onStart();
        mUserPresenter.getUser(mPost.getIdUser());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_post_view, container,false);
        mToolBar = (Toolbar) rootView.findViewById(R.id.pv_tbTitleBar);
        mIvUserPicture = (ImageView) rootView.findViewById(R.id.pv_ivProfilePicture);
        mIvLike = (ImageView) rootView.findViewById(R.id.pv_ivlike);
        mIvDislike = (ImageView) rootView.findViewById(R.id.pv_ivdislike);
        mIvComments = (ImageView) rootView.findViewById(R.id.pv_ivComments);
        mTxvUserName = (TextView) rootView.findViewById(R.id.pv_txvUserName);
        mTxvPostTitle = (TextView) rootView.findViewById(R.id.pv_txvPostTitle);
        mTxvPostDescription = (TextView) rootView.findViewById(R.id.pv_txvPostDescription);
        mTxvPostScore = (TextView) rootView.findViewById(R.id.pv_txvPostScore);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createOnOptionsItemSelected();
        mToolBar.setOnMenuItemClickListener(mListenerOnMenuClick);
        mToolBar.inflateMenu(R.menu.menu_post_view);
        adapteMenu(mToolBar.getMenu());
        mIvComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.showPostComments();
            }
        });

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

    private void fillWidgets(){
        Picasso.with(getContext()).load(mUser.getProfilePicture()).into(mIvUserPicture);

        mTxvUserName.setText(mUser.getName());
        mTxvPostTitle.setText(mPost.getTitle());
        mTxvPostDescription.setText(mPost.getDescription());
    }

    /**
     * Adapt the option menu for the current user
     * @param menu menu inflated
     */
    private void adapteMenu(Menu menu){
        // getMenuInflater().inflate(R.menu.menu_post_view, menu);
        /* switch (Users_Repository.get().getCurrentUser().getTypeUser()){
             case AllConstants.ADMIN_TYPE_ID://Is an admin

                 if (mPost.isPublicate())
                     menu.findItem(R.id.action_pv_ToPublished).setVisible(false);
                 if (mPost.isFixed()) {
                     menu.findItem(R.id.action_pv_ToFixes).setVisible(false);
                 }
                 break;

             case AllConstants.NORMALUSER_TYPE_ID: // In a current user
*/
                 menu.findItem(R.id.action_pv_Edit).setVisible(false);
                 menu.findItem(R.id.action_pv_SendMessage).setVisible(false);
                 menu.findItem(R.id.action_pv_ToFixes).setVisible(false);
                 menu.findItem(R.id.action_pv_ToPublished).setVisible(false);
                 menu.findItem(R.id.action_pv_Delete).setVisible(false);
        /*         break;
         }*/
       // If the current user is the owner of the current post
       if (Users_Repository.get().currentUserIsOwner(mPost)){
           //  if (!mPost.isFixed() && !Users_Repository.get().getCurrentUser().isAdmin())
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
                        createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_ToFixes, TO_FIXED).show();
                        break;
                    case R.id.action_pv_ToPublished:
                        // Crea un cuadro de dialogo que pregunta si quiere hacer la acción y segun el ultimo parametro que le pases hace una acción u otra al darle al okay
                        createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_ToPublished, TO_PUBLISHED).show();
                        break;
            /*case R.id.action_SendMessage:
                // Todo Aqui meter para enviar mensaje
                break;*/
            /*case R.id.action_Edit:
                // Todo Aqui meter para editar
                break;*/
                    case R.id.action_pv_Delete:
                        // Crea un cuadro de dialogo que pregunta si quiere hacer la acción y segun el ultimo parametro que le pases hace una acción u otra al darle al okay
                        createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_Delete, DELETE).show();
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
     * @return return a dialog of this
     */
    public AlertDialog createSimpleDialog(final int idPost, int message, @PostViewPresenter.View.ACTION final int typeAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.dialogAlertTitle)
                .setMessage(message)
                .setPositiveButton(R.string.btnOk,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (typeAction){
                                    case TO_NOT_PUBLISHED:
                                        mPresenter.changePostOfList(mPost,Post.NOT_PUBLISHED);
                                        break;
                                    case TO_PUBLISHED:
                                        mPresenter.changePostOfList(mPost,Post.PUBLISHED);
                                        break;
                                    case TO_FIXED:
                                        mPresenter.changePostOfList(mPost,Post.FIXED);
                                        break;
                                    case DELETE:
                                        mPresenter.deletePost(mPost);
                                        break;
                                }
                                getActivity().onBackPressed();
                            }
                        })
                .setNegativeButton(R.string.btnCancel,null);

        return builder.create();
    }

}
