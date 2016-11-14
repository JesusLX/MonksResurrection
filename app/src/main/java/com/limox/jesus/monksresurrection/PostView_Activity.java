package com.limox.jesus.monksresurrection;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.Singleton.Posts_Singleton;
import com.limox.jesus.monksresurrection.Singleton.Users_Singleton;

public class PostView_Activity extends AppCompatActivity {

    Post mPost;
    Toolbar mToolBar;
    ImageView mIvUserPicture;
    TextView mTxvUserName;
    TextView mTxvPostTitle;
    TextView mTxvPostDescription;

    Toolbar.OnMenuItemClickListener mListenerOnMenuClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        Bundle bundle = this.getIntent().getExtras();

        this.mPost = Posts_Singleton.getPosts_Singleton().getPost(bundle.getInt("idPost"));
        mToolBar = (Toolbar) findViewById(R.id.pv_tbTitleBar);
        mToolBar.inflateMenu(R.menu.menu_post_view);

        CreateOnOptionsItemSelected();
        mToolBar.setOnMenuItemClickListener(mListenerOnMenuClick);

        mIvUserPicture = (ImageView) findViewById(R.id.pv_ivUserPicture);
        mTxvUserName = (TextView) findViewById(R.id.pv_txvUserName);
        mTxvPostTitle = (TextView) findViewById(R.id.pv_txvPostTitle);
        mTxvPostDescription = (TextView) findViewById(R.id.pv_txvPostDescription);
        fillWidgets();
    }
    private void fillWidgets(){
        mIvUserPicture.setImageResource(Users_Singleton.getUsers_Singleton().getUserById(mPost.getIdUser()).getProfilePicture());
        mTxvUserName.setText(Users_Singleton.getUsers_Singleton().getUserById(mPost.getIdUser()).getNick());
        mTxvPostTitle.setText(mPost.getTitle());
        mTxvPostDescription.setText(mPost.getDescription());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_view, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void CreateOnOptionsItemSelected() {
        this.mListenerOnMenuClick = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_ToFixes:
                        // Crea un cuadro de dialogo que pregunta si quiere hacer la acción y segun el ultimo parametro que le pases hace una acción u otra al darle al okay
                        createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_ToFixes, 0).show();
                        break;
                    case R.id.action_ToPublished:
                        // Crea un cuadro de dialogo que pregunta si quiere hacer la acción y segun el ultimo parametro que le pases hace una acción u otra al darle al okay
                        createSimpleDialog(mPost.getIdPost(), R.string.dat_MessageAlert_ToPublished, 1).show();
                        break;
            /*case R.id.action_SendMessage:
                // Todo Aqui meter para enviar mensaje
                break;*/
            /*case R.id.action_Edit:
                // Todo Aqui meter para editar
                break;*/
                    case R.id.action_Delete:
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.dialogAlertTitle)
                .setMessage(message)
                .setPositiveButton(R.string.btnOk,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (typeAction){
                                    case 0:
                                        Posts_Singleton.getPosts_Singleton().toFixPost(idPost);
                                        finish();
                                        break;
                                    case 1:
                                        Posts_Singleton.getPosts_Singleton().toPublicPost(idPost);
                                        finish();
                                        break;
                                    case 2:
                                        Posts_Singleton.getPosts_Singleton().deletePost(idPost);
                                        finish();
                                        break;
                            }}
                        })
                .setNegativeButton(R.string.btnCancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }
}
