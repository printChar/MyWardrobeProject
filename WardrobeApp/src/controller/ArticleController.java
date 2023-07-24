package controller;

import model.dto.Article;
import service.ArticleService;
import view.ArticleView;

import java.util.List;

public class ArticleController {

   private ArticleView view;
   private ArticleService model;
   private Article dtoArticle;

   public ArticleController(ArticleView view, ArticleService model) {
      this.view = view;
      this.model = model;
      updateView();
   }

   private void updateView(){
      List<Article> allVotes = model.findAll();

   }
}
