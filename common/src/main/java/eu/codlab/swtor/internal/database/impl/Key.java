package eu.codlab.swtor.internal.database.impl;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Index;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by kevinleperf on 08/01/16.
 */
@Table(name = "Key", database = Database.class)
public class Key extends BaseModel {

    /**
     * Represent the current Key id,
     * internally, having mId = default means that
     * the object was not saved in the database
     */
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    long mId;

    /**
     * The Key displayed name
     *
     * Not used for now, but here to be prepared to a future
     * key management feature
     */
    @Index
    @Column(name = "name")
    String mName;

    /**
     * The generated code given by the SWTOR Website
     * It is b32 string
     */
    @Column(name = "secret")
    String mSecret;

    /**
     * Internally, give the last update time of the current key
     *
     * selecting a key will update this field
     * hence, the last updated key is the selected one
     */
    @Column(name = "updated_at")
    long mUpdatedAt;

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setSecret(String secret) {
        mSecret = secret;
    }

    public String getSecret() {
        return mSecret;
    }

    public void setUpdatedAt(long updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public long getUpdatedAt() {
        return mUpdatedAt;
    }

}
