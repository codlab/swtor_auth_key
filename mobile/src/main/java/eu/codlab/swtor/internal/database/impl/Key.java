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

    @PrimaryKey(autoincrement = true)
    @Column
    long id;

    @Index
    @Column
    String name;

    @Column
    String secret;
}
