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

import com.bumptech.glide.Glide;
import com.limox.jesus.teambeta.Interfaces.ChatsManagerPresenter;
import com.limox.jesus.teambeta.Interfaces.PostViewPresenter;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.ChatsManagerPresenterImpl;
import com.limox.jesus.teambeta.Presenter.PostViewPresenterImpl;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class PostView_Fragment extends Fragment implements PostViewPresenter.View, UserManagerPresenter.View, ChatsManagerPresenter.View.ChatsManager {

    private User mUser;
    private Post mPost;
    private Toolbar mToolBar;
    private ImageView mIvUserPicture;
    private ImageView mIvComments;
    private TextView mTxvUserName;
    private TextView mTxvState;
    private TextView mTxvPostTitle;
    private TextView mTxvPostDescription;
    private PostViewPresenter mPresenter;
    private UserManagerPresenter mUserPresenter;
    private ChatsManagerPresenterImpl mChatsPresenter;
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
        fillWidgetsUser();

    }

    @Override
    public void onError(Exception exception) {

    }


    @Override
    public void onChatsReceived(HashMap<String, ArrayList<Chat>> singleChatData) {

    }

    @Override
    public void onChatReceived(Chat chat) {
        mCallback.startChat(chat.optBundle());
    }

    public interface OnPostViewFragmentListener {
        void startUserProfile(Bundle user);

        void showPostComments(String idPost);

        void startChat(Bundle chat);
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
            throw new ClassCastException(activity.toString() + " must implement OnPostViewFragmentListener");
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
        mPost = new Post();
        mPost = getArguments().getParcelable(AllConstants.Keys.Parcelables.POST_PARCELABLE_KEY);
        mPost.setCreationDate(new Date(getArguments().getLong(AllConstants.Keys.Parcelables.POST_CREATION_DATE)));
        mUserPresenter = new UserManagerPresenterImpl(this);
        mChatsPresenter = new ChatsManagerPresenterImpl(this);
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
        View rootView = inflater.inflate(R.layout.fragment_post_view, container, false);
        mToolBar = (Toolbar) rootView.findViewById(R.id.pv_tbTitleBar);
        mIvUserPicture = (ImageView) rootView.findViewById(R.id.pv_ivProfilePicture);
        mIvComments = (ImageView) rootView.findViewById(R.id.pv_ivComments);
        mTxvUserName = (TextView) rootView.findViewById(R.id.pv_txvUserName);
        mTxvPostTitle = (TextView) rootView.findViewById(R.id.pv_txvPostTitle);
        mTxvState = (TextView) rootView.findViewById(R.id.pv_txvState);
        mTxvPostDescription = (TextView) rootView.findViewById(R.id.pv_txvPostDescription);
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
                mCallback.showPostComments(mPost.getIdPost());
            }
        });
        switch (mPost.getState()) {

            case Post.ALL:

                break;
            case Post.FIXED:
                mTxvState.setText(R.string.fixed);
                break;
            case Post.ON_REVISION:
                mTxvState.setText(R.string.revision);
                break;
            case Post.PUBLISHED:
                mTxvState.setText(R.string.published);
                break;
        }
        if (Users_Repository.get().getCurrentForum() != null)
            mToolBar.setBackgroundColor(UIUtils.parseColor(Users_Repository.get().getCurrentForum().getColor()));

        if (mPost != null) {
            mTxvPostTitle.setText(mPost.getTitle());
            mTxvPostDescription.setText(mPost.getDescription());
        }
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

    private void fillWidgetsUser() {
        if (isAdded()) {
            Glide.with(getContext()).load(mUser.getProfilePicture()).into(mIvUserPicture);

            mTxvUserName.setText(mUser.getName());
        }

    }

    /**
     * Adapt the option menu for the current user
     *
     * @param menu menu inflated
     */
    private void adapteMenu(Menu menu) {
        if (Users_Repository.get().getCurrentForum() != null) {

            menu.findItem(R.id.action_pv_SendMessage).setVisible(true);
            menu.findItem(R.id.action_pv_Edit).setVisible(/*Users_Repository.get().currentUserCanAdmin(mPost)*/false);
            menu.findItem(R.id.action_pv_ToFixes).setVisible(Users_Repository.get().currentUserCanAdmin(mPost) && !mPost.isFixed());
            menu.findItem(R.id.action_pv_ToPublished).setVisible(Users_Repository.get().currentUserCanAdmin(mPost) && !mPost.isPublicate());
            menu.findItem(R.id.action_pv_Delete).setVisible(Users_Repository.get().currentUserIsOwner(mPost) || Users_Repository.get().currentUserCanAdmin(mPost) && !mPost.isPublicate());
            menu.findItem(R.id.action_pv_SendMessage).setVisible(!Users_Repository.get().currentUserIsOwner(mPost));
        } else {
            menu.findItem(R.id.action_pv_SendMessage).setVisible(false);
            menu.findItem(R.id.action_pv_Edit).setVisible(false);
            menu.findItem(R.id.action_pv_ToFixes).setVisible(false);
            menu.findItem(R.id.action_pv_ToPublished).setVisible(false);
            menu.findItem(R.id.action_pv_Delete).setVisible(false);
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
                if (Users_Repository.get().getCurrentForum() != null) {
                    if (UIUtils.isNetworkAvailable(getContext())) {
                        switch (item.getItemId()) {
                            case R.id.action_pv_ToFixes:
                                // Crea un cuadro de dialogo que pregunta si quiere hacer la acción y segun el ultimo parametro que le pases hace una acción u otra al darle al okay
                                createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_ToFixes, TO_FIXED).show();
                                break;
                            case R.id.action_pv_ToPublished:
                                // Crea un cuadro de dialogo que pregunta si quiere hacer la acción y segun el ultimo parametro que le pases hace una acción u otra al darle al okay
                                createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_ToPublished, TO_PUBLISHED).show();
                                break;
                            case R.id.action_pv_SendMessage:
                                mChatsPresenter.optChat(Users_Repository.get().getCurrentForum().getKey(), Users_Repository.get().getCurrentUser().optChats(Users_Repository.get().getCurrentForum().getKey()), new String[]{Users_Repository.get().getCurrentUser().getId(), mPost.getIdUser()}, null);
                                break;
            /*case R.id.action_Edit:
                // Todo Aqui meter para editar
                break;*/
                            case R.id.action_pv_Delete:
                                // Crea un cuadro de dialogo que pregunta si quiere hacer la acción y segun el ultimo parametro que le pases hace una acción u otra al darle al okay
                                createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_Delete, DELETE).show();
                                break;
                        }
                    } else {
                        UIUtils.toast(getContext(), getString(R.string.connection_error));
                    }
                }
                return true;
            }
        };

    }

    /**
     * Crea un cuadro de dialogo que hace una acción al darle al Ok
     * Dependiendo del typeAction hace
     * case 0:
     * Envía la publicación a la lista de arreglados y cierra la activity
     * case 1:
     * Envía la publicación a la lista de publicados y cierra la activity
     * case 2:
     * Borra la publicación
     *
     * @param idPost     identificador de la publicacion
     * @param message    Mensaje que se va a mostrar en el dialog
     * @param typeAction tipo de accion
     * @return return a dialog of this
     */
    public AlertDialog createSimpleDialog(final String idPost, int message, @PostViewPresenter.View.ACTION final int typeAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.dialogAlertTitle)
                .setMessage(message)
                .setPositiveButton(R.string.btnOk,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (typeAction) {
                                    case TO_NOT_PUBLISHED:
                                        mPresenter.changePostOfList(mPost, Post.ON_REVISION);
                                        break;
                                    case TO_PUBLISHED:
                                        mPresenter.changePostOfList(mPost, Post.PUBLISHED);
                                        break;
                                    case TO_FIXED:
                                        mPresenter.changePostOfList(mPost, Post.FIXED);
                                        break;
                                    case DELETE:
                                        mPresenter.deletePost(mPost.getIdPost());
                                        break;
                                }
                                getActivity().onBackPressed();
                            }
                        })
                .setNegativeButton(R.string.btnCancel, null);

        return builder.create();
    }

}
