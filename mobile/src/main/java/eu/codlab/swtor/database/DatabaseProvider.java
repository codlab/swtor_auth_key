package eu.codlab.swtor.database;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.runtime.transaction.BaseTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by kevinleperf on 16/01/16.
 */
public class DatabaseProvider {

    public DatabaseProvider() {

    }

    public void getKeysAsync(@NonNull final IDatabaseListener listener) {
        new Select()
                .from(Key.class)
                .async()
                .queryList(new TransactionListener<List<Key>>() {
                    @Override
                    public void onResultReceived(List<Key> result) {
                        listener.onGameKeys(result);
                    }

                    @Override
                    public boolean onReady(BaseTransaction<List<Key>> transaction) {
                        return false;
                    }

                    @Override
                    public boolean hasResult(BaseTransaction<List<Key>> transaction, List<Key> result) {
                        return false;
                    }
                });
    }

}
