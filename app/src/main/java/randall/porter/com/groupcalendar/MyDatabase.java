package randall.porter.com.groupcalendar;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by chris_000 on 3/11/2017.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MyDatabase";
    public static final int VERSION = 2;

    @Migration(version = 2, database = MyDatabase.class)
    public static class Migration2 extends AlterTableMigration<CalendarEvent> {

        public Migration2(Class<CalendarEvent> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "CalendarID");
        }
    }

}
