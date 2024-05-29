package app.controllers;

import io.javalin.http.Handler;

public interface ICarEnityControler {

    Handler getAll();
    Handler getById();

    Handler create();
    Handler update();
    Handler delete();


}
