package me.icxd.biblioteka.model.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import me.icxd.biblioteka.app.MyApplication;
import me.icxd.biblioteka.config.DoubanAPI;
import me.icxd.biblioteka.model.bean.Book;
import me.icxd.biblioteka.model.bean.DoubanBook;


public class DataManager {

    // API: Merr librin me anen e ISBN
    public static void getBookInfoFromISBN(String isbn, Response.Listener listener, Response.ErrorListener errorListener) {
        String url = DoubanAPI.bookISBNApi + isbn;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, listener, errorListener);
        MyApplication.getRequestQueue().add(jsObjRequest);
    }

    // API: Merr librin me kerkim
    public static void getBookSearch(String bookName, int start, Response.Listener listener, Response.ErrorListener errorListener) {
        // URLencode
        try {
            bookName = URLEncoder.encode(bookName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(DoubanAPI.bookSearchApi, bookName, start);
        Log.i("API", url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, listener, errorListener);

        MyApplication.getRequestQueue().add(jsObjRequest);
    }

    // JSONObject -> DoubanBook
    public static DoubanBook jsonObject2DoubanBook(JSONObject book) {
        DoubanBook bookData = new DoubanBook();
        try {
            // rating
            DoubanBook.RatingEntity ratingEntity = new DoubanBook.RatingEntity();
            ratingEntity.setMax(book.getJSONObject("rating").getInt("max"));
            ratingEntity.setNumRaters(book.getJSONObject("rating").getInt("numRaters"));
            ratingEntity.setAverage(book.getJSONObject("rating").getString("average"));
            ratingEntity.setMin(book.getJSONObject("rating").getInt("min"));
            bookData.setRating(ratingEntity);

            bookData.setSubtitle(book.getString("subtitle"));
            bookData.setPubdate(book.getString("pubdate"));
            bookData.setOrigin_title(book.getString("origin_title"));
            bookData.setImage(book.getString("image"));
            bookData.setBinding(book.getString("binding"));
            bookData.setCatalog(book.getString("catalog"));
            bookData.setPages(book.getString("pages"));

            // images
            DoubanBook.ImagesEntity imagesEntity = new DoubanBook.ImagesEntity();
            imagesEntity.setSmall(book.getJSONObject("images").getString("small"));
            imagesEntity.setMedium(book.getJSONObject("images").getString("medium"));
            imagesEntity.setLarge(book.getJSONObject("images").getString("large"));
            bookData.setImages(imagesEntity);

            bookData.setAlt(book.getString("alt"));
            bookData.setId(book.getString("id"));
            bookData.setPublisher(book.getString("publisher"));
            bookData.setIsbn10(book.getString("isbn10"));
            bookData.setIsbn13(book.has("isbn13") ? book.getString("isbn13") : "");
            bookData.setTitle(book.getString("title"));
            bookData.setUrl(book.getString("url"));
            bookData.setAlt_title(book.getString("alt_title"));
            bookData.setAuthor_intro(book.getString("author_intro"));
            bookData.setSummary(book.getString("summary"));
            bookData.setPrice(book.getString("price"));

            // author
            List<String> author = new ArrayList<>();
            JSONArray authors = book.getJSONArray("author");
            for (int i = 0; i < authors.length(); i++) {
                author.add(authors.getString(i));
            }
            bookData.setAuthor(author);

            // translators
            List<String> translator = new ArrayList<>();
            JSONArray translators = book.getJSONArray("translator");
            for (int i = 0; i < translators.length(); i++) {
                translator.add(translators.getString(i));
            }
            bookData.setTranslator(translator);

            // tags
            List<DoubanBook.TagsEntity> tags = new ArrayList<>();
            JSONArray tagsJson = book.getJSONArray("tags");
            for (int i = 0; i < tagsJson.length(); i++) {
                DoubanBook.TagsEntity tagsEntity = new DoubanBook.TagsEntity();
                tagsEntity.setCount(tagsJson.getJSONObject(i).getInt("count"));
                tagsEntity.setName(tagsJson.getJSONObject(i).getString("name"));
                tagsEntity.setTitle(tagsJson.getJSONObject(i).getString("title"));
                tags.add(tagsEntity);
            }
            bookData.setTags(tags);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookData;
    }

    // DoubanBook -> Book
    public static Book doubanBook2Book(DoubanBook book) {
        Book book_db = new Book();

        // author
        StringBuilder authorString = new StringBuilder();
        if (book.getAuthor() != null) {
            for (int i = 0; i < book.getAuthor().size(); i++) {
                if (i < book.getAuthor().size() - 1) {
                    authorString.append(book.getAuthor().get(i).toString());
                    authorString.append("、");
                } else {
                    authorString.append(book.getAuthor().get(i).toString());
                }
            }
        }
        book_db.setAuthor(authorString.toString());

        // translators
        StringBuilder translatorString = new StringBuilder();
        if (book.getTranslator() != null) {
            for (int i = 0; i < book.getTranslator().size(); i++) {
                if (i < book.getTranslator().size() - 1) {
                    translatorString.append(book.getTranslator().get(i).toString());
                    translatorString.append("、");
                } else {
                    translatorString.append(book.getTranslator().get(i).toString());
                }
            }
        }
        book_db.setTranslator(translatorString.toString());

        book_db.setAuthor_intro(book.getAuthor_intro());
        book_db.setImage(book.getImages().getLarge());
        book_db.setPages(book.getPages());
        book_db.setOrigin_title(book.getOrigin_title());
        book_db.setBinding(book.getBinding());
        book_db.setCatalog(book.getCatalog());
        book_db.setPrice(book.getPrice());
        book_db.setPubdate(book.getPubdate());
        book_db.setSubtitle(book.getSubtitle());
        book_db.setSummary(book.getSummary());
        book_db.setTitle(book.getTitle());
        book_db.setUrl(book.getUrl());
        book_db.setAlt(book.getAlt());
        book_db.setPublisher(book.getPublisher());
        book_db.setIsbn13(book.getIsbn13().isEmpty() ? book.getIsbn10() : book.getIsbn13());
        book_db.setAverage(book.getRating().getAverage());
        book_db.setFavourite(false);
        book_db.setNote("");
        book_db.setNote_date("");

        // tags
        if (book.getTags() != null) {
            if (book.getTags().size() >= 3) {
                book_db.setTag1(book.getTags().get(0).getName());
                book_db.setTag2(book.getTags().get(1).getName());
                book_db.setTag3(book.getTags().get(2).getName());
            } else {
                switch (book.getTags().size()) {
                    case 0:
                        break;
                    case 1:
                        book_db.setTag1(book.getTags().get(0).getName());
                        break;
                    case 2:
                        book_db.setTag1(book.getTags().get(0).getName());
                        book_db.setTag2(book.getTags().get(1).getName());
                        break;
                }
            }
        }
        return book_db;
    }

    public static List<Book> mbushLibra(){
        List<Book> listaLiber = new ArrayList<>();
        listaLiber.add(krijoLiber("Novelat e Qytetit te Veriut", "Migjeni", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/8-large_default/vargjet-e-lira-novelat-e-qytetit-te-veriut-migjeni.jpg", "8", "150", "http://www.shtepiaelibrit.com/store/sq/letersia-shqiptare/9-vargjet-e-lira-novelat-e-qytetit-te-veriut-migjeni.html", "1", "Poezi"));
        listaLiber.add(krijoLiber("Ali Pashe Tepelena", "Sabri Godo", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/19-large_default/ali-pashe-tepelena-sabri-godo.jpg", "5","210", "http://www.shtepiaelibrit.com/store/sq/romane/20-ali-pashe-tepelena-sabri-godo.html","1", "Historik"));
        listaLiber.add(krijoLiber("Piramida", "Ismail Kadare", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/3977-large_default/piramida-ismail-kadare.jpg", "6","155", "http://www.shtepiaelibrit.com/store/sq/romane/215-piramida-ismail-kadare.html","1", "Historik"));
        listaLiber.add(krijoLiber("Lot te padukshem", "Anton Cehov", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/4691-large_default/lot-te-padukshem-anton-cehov.jpg", "8","178", "http://www.shtepiaelibrit.com/store/sq/klasiket/185-lot-te-padukshem-anton-cehov.html","2", "Tregime"));
        listaLiber.add(krijoLiber("Ujku i Stepes", "Hernan Hesse", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/7822-large_default/ujku-i-stepes-hermann-hesse.jpg", "10", "240", "http://www.shtepiaelibrit.com/store/sq/nobelistet/63-ujku-i-stepes-hermann-hesse.html","2", "Filozofik"));
        listaLiber.add(krijoLiber("I Huaji", "Albert Kamy", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/4209-large_default/i-huaji-albert-kamy.jpg", "9", "380", "http://www.shtepiaelibrit.com/store/sq/libra-me-kupon/433-i-huaji-albert-kamy.html", "2", "Filozofik"));
        listaLiber.add(krijoLiber("Vepra 1", "Lasgush Poradeci", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/1277-large_default/poezia-vepra-i-lasgush-poradeci.jpg", "7", "203", "http://www.shtepiaelibrit.com/store/sq/poezi/1133-poezia-vepra-i-lasgush-poradeci.html","1", "Poezi"));
        listaLiber.add(krijoLiber("Drama", "Kasem Trebeshina", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/8074-large_default/drama-antologji-personale-1937-2006-kasem-trebeshina.jpg", "7", "145", "http://www.shtepiaelibrit.com/store/sq/drama-shqip/1307-drama-antologji-personale-1937-2006-kasem-trebeshina.html","1", "Drame"));
        listaLiber.add(krijoLiber("30 Poezi", "Umberto Saba", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/875-large_default/30-poezi-umberto-saba.jpg", "5", "365", "http://www.shtepiaelibrit.com/store/sq/poezia/781-30-poezi-umberto-saba.html","2", "Poezi"));
        listaLiber.add(krijoLiber("Iliada", "Homeri", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/868-large_default/iliada-homeri.jpg", "10", "245", "http://www.shtepiaelibrit.com/store/sq/letersia-antike/774-iliada-homeri.html","2", "Antike"));
        listaLiber.add(krijoLiber("Antigona", "Sofokliu", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/879-large_default/antigona-sofokliu.jpg", "8", "287", "http://www.shtepiaelibrit.com/store/sq/leximet-e-veres-2010/785-antigona-sofokliu.html","2", "Antike"));
        listaLiber.add(krijoLiber("Edipi Mbret", "Sofokliu", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/880-large_default/edipi-mbret-sofokliu.jpg", "10", "178", "http://www.shtepiaelibrit.com/store/sq/letersia-antike/786-edipi-mbret-sofokliu.html","2", "Antike"));
        listaLiber.add(krijoLiber("Kodi i Da Vincit", "Dan Brown", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/3412-large_default/kodi-i-da-vincit-dan-brown.jpg", "7", "320", "http://www.shtepiaelibrit.com/store/sq/thriller/50-kodi-i-da-vincit-dan-brown.html","2", "Thriller"));
        listaLiber.add(krijoLiber("Klienti", "John Grisham", "978-99956-43-51-3", "http://www.shtepiaelibrit.com/store/5227-large_default/klienti-john-grisham.jpg", "8", "271", "http://www.shtepiaelibrit.com/store/sq/thriller/70-klienti-john-grisham.html","2", "Thriller"));
        return listaLiber;
    }

    public static Book krijoLiber(String t, String a, String isbn, String foto, String rate, String faqe, String adresa, String tag1, String tag2){
        Book book_l = new Book();
        book_l.setAuthor(a);
        book_l.setTranslator("Nuk ka perkthyes");
        book_l.setAuthor_intro(a);
        book_l.setImage(foto);
        book_l.setPages(faqe);
        book_l.setOrigin_title(t);
        book_l.setBinding("bosh");
        book_l.setCatalog("bosh");
        book_l.setPrice("1500 ALL");
        book_l.setPubdate("2017");
        book_l.setSubtitle("bosh");
        book_l.setSummary("Bosh");
        book_l.setTitle(t);
        book_l.setUrl(adresa);
        book_l.setAlt("bosh");
        book_l.setPublisher("bosh");
        book_l.setIsbn13(isbn);
        book_l.setAverage(rate);
        book_l.setFavourite(false);
        book_l.setNote("Liber demo");
        book_l.setNote_date("10/14/2017");
        book_l.setTag1(tag1);
        book_l.setTag2(tag2);
        return book_l;
    }
}
