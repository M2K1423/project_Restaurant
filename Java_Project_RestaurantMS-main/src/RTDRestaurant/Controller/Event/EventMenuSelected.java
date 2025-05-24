
package RTDRestaurant.Controller.Event;

import java.sql.SQLException;

//Event khi chọn menu (menu index + subMenu index)
public interface EventMenuSelected {
    public void menuSelected(int menuIndex,int subMenuIndex) throws SQLException;
}
