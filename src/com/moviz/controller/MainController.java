package com.moviz.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import com.moviz.service.SearchBuilder;
import com.moviz.service.SecurityContext;
import com.moviz.subview.FilmSubView;
import com.moviz.subview.SideMenuSubView;
import com.moviz.subview.SortBoxSubView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import com.moviz.main.Application;
import com.moviz.subview.MainScrollPaneSubView;
import com.moviz.entity.Film;

/**
 * Controller for the lists of films
 */
public class MainController implements Initializable, Observer {

    /*
     * View
     */
    @FXML
    private ListView<String> menu;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane listPane;

    @FXML
    private ChoiceBox<String> sortChoiceBox;

    @FXML
    private Text countFilms;

    /*
     * Services
     */
    /**
     * List renderer to display the list of films using filters and sorts
     */
    protected SearchBuilder searchBuilder;


    /**
     * Initialize the controller and the view
     *
     * @param url The Scene URL
     * @param resourceBundle The resources bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.searchBuilder = Application.getSearchBuilder();
        this.searchBuilder.addObserver(this);


        // Render sub-view elements
        SideMenuSubView.renderIn(this.menu);
        SortBoxSubView.renderIn(this.sortChoiceBox);
        MainScrollPaneSubView.renderIn(this.scrollPane, this.listPane);


        // Default options (use static parameters in order to keep options when scene change)
        this.menu.getSelectionModel().select(this.searchBuilder.getFilter());
        this.sortChoiceBox.getSelectionModel().select(this.searchBuilder.getSort());


        // Events
        this.menu.getSelectionModel().selectedItemProperty().addListener(
            new FilterOptionHandler(this.menu.getItems(), this)
        );

        this.sortChoiceBox.getSelectionModel().selectedItemProperty().addListener(
            new SortOptionHandler(this.sortChoiceBox.getItems(), this)
        );


        // Render default list
        this.render();
    }

    /**
     * Implement the Observer update method to re-render the view on each search option modification
     */
    @Override
    public void update(Observable searchBuilder, Object payload) {
        this.render();
    }

    /**
     * Render the current search results
     */
    public void render() {
        ArrayList<Film> films = this.searchBuilder.search();

        // View
        this.listPane.getChildren().clear();

        for (Film film: films) {
            Button button = FilmSubView.createListButton(film);
            button.setOnAction(new FilmChosenHandler(film));

            this.listPane.getChildren().add(button);
        }

        this.countFilms.setText(films.size() + " films");
    }

    /**
     * Search builder getter
     *
     * @return Search builder
     */
    public SearchBuilder getSearchBuilder() {
        return searchBuilder;
    }



    /*
     * Handlers
     */

    /**
     * Sort option handler: re-render the list using the given sort option
     */
    private class SortOptionHandler implements ChangeListener<String> {
        private ObservableList<String> sorts;
        private MainController controller;

        public SortOptionHandler(ObservableList<String> sorts, MainController controller) {
            this.sorts = sorts;
            this.controller = controller;
        }

        @Override
        public void changed(ObservableValue<? extends String> observable, String old, String sortName) {
            this.controller.getSearchBuilder().setSort(sorts.indexOf(sortName));
        }
    }

    /**
     * Filter option handler: re-render the list using the given filter option
     */
    private class FilterOptionHandler implements ChangeListener<String> {
        private ObservableList<String> filters;
        private MainController controller;

        public FilterOptionHandler(ObservableList<String> filters, MainController controller) {
            this.filters = filters;
            this.controller = controller;
        }

        @Override
        public void changed(ObservableValue<? extends String> observable, String old, String filterName) {
            this.controller.getSearchBuilder().setFilter(filters.indexOf(filterName));
        }
    }

    /**
     * Film chosen handler: handle the click on a film button
     */
    private class FilmChosenHandler implements EventHandler<ActionEvent> {
        protected Film chosenFilm;

        public FilmChosenHandler(Film chosenFilm) {
            this.chosenFilm = chosenFilm;
        }

        @Override
        public void handle(ActionEvent event) {
            Application.showReadStage(chosenFilm);
        }
    }

}
