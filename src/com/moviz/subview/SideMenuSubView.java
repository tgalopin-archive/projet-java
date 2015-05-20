package com.moviz.subview;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

/**
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class SideMenuSubView {

    /**
     * Render the side menu
     *
     * @param menu The menu object to edit
     */
    public static void renderIn(ListView menu) {
        menu.setItems(FXCollections.observableArrayList(
            "Tous les films",
            "Films non-notés",
            "Films notés",
            "Films regardés",
            "Films à regarder"
        ));
    }

}
