package com.example.litness.litness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.example.litness.litness.Bar.Day;
import com.example.litness.litness.Bar.Review;
import com.example.litness.litness.Bar.Item;
import com.example.litness.litness.Bar.Menu;




import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client extends AppCompatActivity {

    public static String currentUserName = "";
    public static HashMap<String, Bar> barMap;
    public static Bar activeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);

        barMap = new HashMap<>();

        populateBarMap();
        launchFirstActivity();
        finish();
    }

    private void populateBarMap() {
        List <Integer> defPhotos = new ArrayList<>();
        defPhotos.add(R.drawable.img_template0);
        defPhotos.add(R.drawable.img_template1);
        defPhotos.add(R.drawable.img_template2);
        defPhotos.add(R.drawable.img_template3);
        defPhotos.add(R.drawable.img_template4);

        List<Integer> defLive = new ArrayList<>();
        defLive.add(R.drawable.img_template_live0);
        defLive.add(R.drawable.img_template_live1);
        defLive.add(R.drawable.img_template_live2);

        Menu defMenu = new Menu();
        defMenu.drinks.add(new Item("Bud Light", 4));
        defMenu.drinks.add(new Item("Coors", 4));
        defMenu.drinks.add(new Item("Miller Lite", 4));
        defMenu.drinks.add(new Item("Bud Light Platinum", 5));
        defMenu.drinks.add(new Item("Black Warrior Crimson Ale", 5));
        defMenu.food.add(new Item("Burger", 8));
        defMenu.food.add(new Item("Fries", 2));
        defMenu.food.add(new Item("Chicken Fingers", 8));
        defMenu.food.add(new Item("Onion Rings", 3));

        List<Review> defReviews = new ArrayList<>();
        Review r = new Review();
        r.user = "John Doe";
        r.text = "Great place to meet friends";
        r.rating = "3";
        defReviews.add(r);
        r = new Review();
        r.user = "Jane Doe";
        r.text = "I met my husband here!";
        r.rating = "5";
        defReviews.add(r);
        r = new Review();
        r.user = "Thomas Frank";
        r.text = "I always come here after work for a pint";
        r.rating = "5";
        defReviews.add(r);

        List<Day> defDay = new ArrayList<>();
        Day d = new Day();
        d.day = "Sunday";
        d.events.add("Acoustic Band");
        defDay.add(d);
        d = new Day();
        d.day = "Monday";
        d.specials.add("$2 Shots");
        defDay.add(d);
        d = new Day();
        d.day = "Tuesday";
        d.specials.add("$3 Bud Lights");
        d.events.add("Local Band");
        defDay.add(d);d = new Day();
        d.day = "Wednesday";
        d.events.add("DJ");
        defDay.add(d);d = new Day();
        d.day = "Thursday";
        d.specials.add("$3 Pitchers");
        d.events.add("DJ");
        defDay.add(d);d = new Day();
        d.day = "Friday";
        d.specials.add("$3 Pitchers");
        d.events.add("DJ");
        defDay.add(d);
        d = new Day();
        d.day = "Saturday";
        d.specials.add("$4 Drafts");
        d.events.add("Frank Hurts");
        defDay.add(d);



        //Template for bar info input
        Bar bar = new Bar();
        bar.barName = "Rounders";
        bar.coverOver = "$10";
        bar.coverUnder="$20";
        bar.wait = "10 Minutes";
        bar.litness = "5";
        bar.phone = "(205) 345-4848";
        bar.address = "1215 University Blvd, Tuscaloosa, AL 35401";
        bar.description = "Cool nightclub with 3 bars & live music, plus an open-air rooftop space, sports on TV & VIP tables.";
        bar.rating = "3.9";

        Day today = new Day();
        today.day = "Sunday";
        today.events.add("Jon Langston");
        today.events.add("Lil Peep");
        today.specials.add("$5 Tequila Shots");
        today.specials.add("$2 Bud Light");
        //always put at index 0. The adapters just look there now so we don't have to come up with so many specials
        bar.days.add(today);

        today = new Day();
        today.day = "Monday";
        today.events.add("Bingo Night");
        today.specials.add("2-for-1 Margaritas");
        bar.days.add(today);

        today = new Day();
        today.day = "Wednesday";
        today.events.add("Midweek Mania: DJ Snake");
        //today.specials.add("Half-priced shots");
        bar.days.add(today);

        today = new Day();
        today.day = "Friday";
        //today.events.add("Travis Scott");
        today.specials.add("$1 Ladies' Drinks");
        bar.days.add(today);

        today = new Day();
        today.day = "Saturday";
        today.events.add("DJ Crunk");
        today.specials.add("$2 Beers");
        today.specials.add("$5 Mixed Drinks");
        bar.days.add(today);

        Review review = new Review();
        review.rating = "5";
        review.text = "SO MUCH FUN!!! LOUD AND GOOD DRINKS";
        review.user = "James Frank";
        bar.reviews.add(review);

        bar.photos.add(R.drawable.img_rounders0);
        bar.photos.add(R.drawable.img_rounders1);
        bar.photos.add(R.drawable.img_rounders2);

        bar.livePhotos.add(R.drawable.img_rounders0);
        bar.livePhotos.add(R.drawable.img_rounders1);
        bar.livePhotos.add(R.drawable.img_rounders2);

        bar.tags.add("Night Clubs");
        bar.tags.add("Under 21");
        bar.tags.add("All Bars");

        bar.menu.drinks.add(new Item("Bud Light", 3));
        bar.menu.drinks.add(new Item("Trim Tab IPA", 4));
        bar.menu.drinks.add(new Item("Good People Pale Ale", 4));
        bar.menu.drinks.add(new Item("Trim Tab Paradise Now", 4));
        bar.menu.drinks.add(new Item("Bud Light", 3));
        bar.menu.drinks.add(new Item("Trim Tab IPA", 4));
        bar.menu.drinks.add(new Item("Good People Pale Ale", 4));
        bar.menu.drinks.add(new Item("Trim Tab Paradise Now", 4));

        barMap.put(bar.barName,bar);
        //End Rounders

        //Loosa Info
        bar = new Bar();
        bar.barName = "Loosa Brews";
        bar.days = defDay;
        bar.phone = "(205) 737-7440";
        bar.address = "412 20th Ave, Tuscaloosa, AL 35401";
        bar.description = "A nice chill bar with a large assortment of beer selections.  Offers games like Mario Kart, Pinball, Ping Pong, board games, and even guitars that you can play.";
        bar.tags.add("Breweries");
        bar.tags.add("Outdoor Venues");
        bar.tags.add("All Bars");
        bar.litness ="2";
        bar.rating = "4.2";

        review = new Review();
        review.rating = "5";
        review.text = "This is a nice bar with a chill environment I'd recommend it to my friends";
        review.user = "Sam Cler";
        bar.reviews.add(review);

        bar.menu.drinks.add(new Item("Cahaba Blood Orange", 5));
        bar.menu.drinks.add(new Item("Trim Tab IPA", 6));
        bar.menu.drinks.add(new Item("Good People Pale Ale", 6));
        bar.menu.drinks.add(new Item("Trim Tab Paradise Now", 5));
        bar.menu.drinks.add(new Item("Good People Strawberry Kiwi", 5));
        bar.menu.drinks.add(new Item("Cahaba Imperial Stout", 6));
        bar.menu.drinks.add(new Item("Good People Golden Ale", 5));
        bar.menu.drinks.add(new Item("Good People Irish Stout", 4));

        bar.photos.add(R.drawable.img_loosa0);
        bar.photos.add(R.drawable.img_loosa1);
        bar.photos.add(R.drawable.img_loosa2);

        bar.livePhotos.add(R.drawable.img_loosa0);
        bar.livePhotos.add(R.drawable.img_loosa1);
        bar.livePhotos.add(R.drawable.img_loosa2);

        barMap.put(bar.barName,bar);
        //End Loosa

        //Innisfree
        bar = new Bar();
        bar.barName = "Innisfree";
        bar.coverOver = "$5";
        bar.wait = "5 Minutes";
        bar.litness = "4";
        bar.phone = "(205) 345-1199";
        bar.address = "1925 University Blvd, Tuscaloosa, AL 35401";
        bar.description = "Fun Irish pub with good outdoor venue and a pool table";
        bar.tags.add("Bars with Food");
        bar.tags.add("Outdoor Venues");
        bar.tags.add("All Bars");
        bar.rating = "4.0";

        today = new Day();
        today.day = "Sunday";
        today.specials.add("$5 Bud Light Pitchers");
        bar.days.add(today);

        today = new Day();
        today.day = "Monday";
        today.specials.add("$5 Bud Light Pitchers");
        bar.days.add(today);

        today = new Day();
        today.day = "Tuesday";
        today.specials.add("$5 Bud Light Pitchers");
        bar.days.add(today);

        today = new Day();
        today.day = "Wednesday";
        today.events.add("Wine Wednesday");
        today.specials.add("$2 Glasses of Wine");
        bar.days.add(today);

        today = new Day();
        today.day = "Thursday";
        today.events.add("Thirsty Thursday");
        today.specials.add("1/2 Priced Irish Car Bombs");
        today.specials.add("$5 Bud Light Pitchers");
        bar.days.add(today);

        today = new Day();
        today.day = "Friday";
        today.specials.add("$2 Bud Lights");
        bar.days.add(today);

        today = new Day();
        today.day = "Saturday";
        today.specials.add("$2 Bud Lights");
        bar.days.add(today);

        review = new Review();
        review.rating = "2";
        review.text = "Expensive cover and warm beer";
        review.user = "Paul Bremer";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "5";
        review.text = "They played MO BAMBA!!!!";
        review.user = "Mike Norman";
        bar.reviews.add(review);

        bar.menu.food.add(new Item("Burger", 5));
        bar.menu.food.add(new Item("Fries", 3));
        bar.menu.food.add(new Item("Fish and Chips", 9));
        bar.menu.food.add(new Item("Nachos", 7));

        bar.menu.drinks.add(new Item("Single Wells", 8));
        bar.menu.drinks.add(new Item("Bud Light", 3));
        bar.menu.drinks.add(new Item("Miller Light", 4));
        bar.menu.drinks.add(new Item("Yellow Hammers", 8));
        bar.menu.drinks.add(new Item("Lemon Drop Shots", 3));
        bar.menu.drinks.add(new Item("Clearwater Shots", 2));

        bar.photos.add(R.drawable.img_innisfree0);
        bar.photos.add(R.drawable.img_innisfree1);
        bar.photos.add(R.drawable.img_innisfree2);
        bar.photos.add(R.drawable.img_innisfree3);

        bar.livePhotos.add(R.drawable.img_innisfree0);
        bar.livePhotos.add(R.drawable.img_innisfree1);
        bar.livePhotos.add(R.drawable.img_innisfree2);
        bar.livePhotos.add(R.drawable.img_innisfree3);

        barMap.put(bar.barName,bar);
        //End Innisfree

        //Start Glory Bound
        bar = new Bar();
        bar.barName = "Glory Bound";
        bar.litness = "1";
        bar.phone = "(205) 349-0505";
        bar.address = "1301 University Blvd Tuscaloosa, Alabama 35401";
        bar.description = "Nice restaurant with Gyros and Pizzas";
        bar.tags.add("Bars with Food");
        bar.tags.add("All Bars");

        bar.photos.add(R.drawable.img_glorybound0);
        bar.photos.add(R.drawable.img_glorybound1);

        bar.livePhotos.add(R.drawable.img_glorybound3);
        bar.livePhotos.add(R.drawable.img_glorybound4);

        today = new Day();
        today.day = "Sunday";
        today.specials.add("$2 Mimosas");
        today.specials.add("$3 Bloody Mary's");
        bar.days.add(today);

        today = new Day();
        today.day = "Monday";
        today.specials.add("$3 Drafts 4:00-Close");
        bar.days.add(today);

        today = new Day();
        today.day = "Tuesday";
        today.specials.add("$5 Gyros");
        bar.days.add(today);

        today = new Day();
        today.day = "Wednesday";
        today.specials.add("$5 Salads");
        bar.days.add(today);

        today = new Day();
        today.day = "Thursday";
        today.specials.add("$5 Gyros with a College ID");
        today.specials.add("$3 Drafts");
        bar.days.add(today);

        today = new Day();
        today.day = "Friday";
        today.specials.add("$3 Fusions");
        bar.days.add(today);

        today = new Day();
        today.day = "Saturday";
        today.specials.add("$2 Mimosas");
        today.specials.add("$3 Bloody Mary's");
        bar.days.add(today);

        review = new Review();
        review.rating = "4";
        review.user = "Jeff Turner";
        review.text = "Nice clean place. Food was good & courteous waitress!!";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "4";
        review.user = "Cindy Shumpert";
        review.text = "Service was good--Cole was our server. Food was delicious and price was good too.";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "2";
        review.user = "Richard Wallace";
        review.text = "Went to the restaurant tonight around 9:45 and the waitress and bartender refused to serve me despite not closing until 10:00 PM.";
        bar.reviews.add(review);

        bar.menu.drinks.add(new Item("Domestics", 4));
        bar.menu.drinks.add(new Item("Imports", 5));
        bar.menu.drinks.add(new Item("Wells", 5));
        bar.menu.drinks.add(new Item("Calls", 6));

        bar.menu.food.add(new Item("Pizza", 5));
        bar.menu.food.add(new Item("Pepper Jack Gyro", 9));
        bar.menu.food.add(new Item("Surf & Turf Gyro", 8));
        bar.menu.food.add(new Item("House Salad", 8));
        bar.menu.food.add(new Item("Bacon Egg and CHeese Gyro", 7));

        barMap.put(bar.barName,bar);
        //End Glory Bound

        //Galletes Bar
        bar = new Bar();
        bar.barName = "Galletes";
        bar.coverOver = "$10";
        bar.wait = "20 Minutes";
        bar.phone = "N/A";
        bar.address = "1104 6th St, Tuscaloosa, AL 35401";
        bar.description = "Funnest 21+ bar in town.  Front room with large screen and back club room for dancing.";
        bar.tags.add("Night Clubs");
        bar.tags.add("Outdoor Venues");
        bar.tags.add("All Bars");
        bar.litness = "5";
        bar.rating = "5";

        today = new Day();
        today.day = "Wednesday";
        today.events.add("Wine Wednesday");
        today.specials.add("$5 Bottles of Wine");
        bar.days.add(today);

        today = new Day();
        today.day = "Thursday";
        today.events.add("Thirsty Thursday");
        today.specials.add("$3 32oz Wells");
        bar.days.add(today);

        today = new Day();
        today.day = "Friday";
        today.events.add("DJ Huckleberry Spin");
        today.specials.add("$5 32oz Wells");
        bar.days.add(today);

        today = new Day();
        today.day = "Saturday";
        today.events.add("DJ Huckleberry Spin");
        today.specials.add("$5 32oz Wells");
        bar.days.add(today);

        bar.menu.drinks.add(new Item("Single Wells", 8));
        bar.menu.drinks.add(new Item("Bud Light", 3));
        bar.menu.drinks.add(new Item("Miller Light", 4));
        bar.menu.drinks.add(new Item("Yellow Hammers", 8));
        bar.menu.drinks.add(new Item("Lemon Drop Shots", 3));
        bar.menu.drinks.add(new Item("Clearwater Shots", 2));

        review = new Review();
        review.rating = "5";
        review.text = "Back room was lit! DJ Huckleberry Spin is the man!";
        review.user = "Mason Goomes";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "4";
        review.text = "The yellow hammers were amazing.  Bar was a little too crowded";
        review.user = "Jake Groeber";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "5";
        review.text = "Back room was lit! DJ Huckleberry Spin is the man!";
        review.user = "Mason Goomes";
        bar.reviews.add(review);

        bar.photos.add(R.drawable.img_galletes0);
        bar.photos.add(R.drawable.img_galletes1);
        bar.photos.add(R.drawable.img_galletes2);
        bar.photos.add(R.drawable.img_galletes3);

        bar.livePhotos.add(R.drawable.img_galletes4);
        bar.livePhotos.add(R.drawable.img_galletes5);

        barMap.put(bar.barName,bar);
        //End Galletes

        //Egans
        bar = new Bar();
        bar.barName = "Egan's Bar";
        bar.phone = "(205) 758-9413";
        bar.address = "1229 University Blvd, Tuscaloosa, AL 35401";
        bar.description = "Weird bar for older people of Tuscaloosa.";
        bar.tags.add("All Bars");
        bar.litness = "1";
        bar.rating = "1";

        today = new Day();
        today.day = "Friday";
        today.events.add("Random Band");
        bar.days.add(today);

        today = new Day();
        today.day = "Saturday";
        today.events.add("Another Band");
        bar.days.add(today);


        bar.menu.drinks.add(new Item("Bud Light", 3));
        bar.menu.drinks.add(new Item("Miller Light", 4));
        bar.menu.drinks.add(new Item("Coors Light", 4));
        bar.menu.drinks.add(new Item("Keystone", 4));

        review = new Review();
        review.rating = "1";
        review.text = "Bar is filled with smoke and is too dark";
        review.user = "Noah Gleason";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "1";
        review.text = "Not a bar for the younger crowd";
        review.user = "Keegan Majers";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "1";
        review.text = "Only people older than 30";
        review.user = "Rob Harrington";
        bar.reviews.add(review);

        bar.photos.add(R.drawable.img_egans0);
        bar.photos.add(R.drawable.img_egans1);
        bar.photos.add(R.drawable.img_egans2);

        bar.livePhotos.add(R.drawable.img_egans0);

        barMap.put(bar.barName,bar);
        //End Egans

        //Houndstooth
        bar = new Bar();
        bar.barName = "The Houndstooth";
        bar.phone = "(205) 752-8444";
        bar.address = "1300 University Blvd, Tuscaloosa, AL 35401";
        bar.description = "Longtime collegial hangout offering darts & pool tables, plus an outdoor patio & sports on many TVs..";
        bar.tags.add("All Bars");
        bar.tags.add("Outdoor Venues");
        bar.litness = "2";
        bar.rating = "4.3";

        today = new Day();
        today.day = "Thursday";
        today.events.add("Bingo Night");
        today.specials.add("$2 Bud Lights");
        bar.days.add(today);

        bar.menu.drinks.add(new Item("Single Wells", 8));
        bar.menu.drinks.add(new Item("Bud Light", 4));
        bar.menu.drinks.add(new Item("Miller Light", 4));
        bar.menu.drinks.add(new Item("Clearwater Shots", 2));
        bar.menu.drinks.add(new Item("Blue Guys", 6));

        review = new Review();
        review.rating = "5";
        review.text = "Bingo Night was fun.  I won $25 to Olive Garden";
        review.user = "Mason Groomes";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "4";
        review.text = "Fun place to hangout with friends and play pool";
        review.user = "Bryan Heath";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "4";
        review.text = "Love the outside seating";
        review.user = "Jake Mizzell";
        bar.reviews.add(review);

        bar.photos.add(R.drawable.img_houndstooth0);
        bar.photos.add(R.drawable.img_houndstooth1);
        bar.photos.add(R.drawable.img_houndstooth2);

        bar.livePhotos.add(R.drawable.img_houndstooth3);
        bar.livePhotos.add(R.drawable.img_houndstooth4);

        barMap.put(bar.barName,bar);
        //Houndstooth End

        //Jackies
        bar = new Bar();
        bar.barName = "Jackie's Lounge";
        bar.phone = "(205) 758-9179";
        bar.address = "2111 Paul W. Bryant Dr, Tuscaloosa, AL 35401";
        bar.description = "Local bar famous for karoake";
        bar.tags.add("All Bars");
        bar.litness = "2";
        bar.rating = "3.5";

        today = new Day();
        today.day = "Monday";
        today.events.add("Free Pool");
        today.specials.add("$3.75 Wells");
        bar.days.add(today);

        today = new Day();
        today.day = "Thursday";
        today.events.add("Karoake Night");
        today.specials.add("$2 Bud Lights");
        bar.days.add(today);

        bar.menu.drinks.add(new Item("Single Wells", 8));
        bar.menu.drinks.add(new Item("Bud Light", 4));
        bar.menu.drinks.add(new Item("Miller Light", 4));

        review = new Review();
        review.rating = "5";
        review.text = "Love the karoake!";
        review.user = "Mason Groomes";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "2";
        review.text = "Whole place smells like smoke";
        review.user = "Scott Smith";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "4";
        review.text = "Can't beat free pool!";
        review.user = "Mike Norman";
        bar.reviews.add(review);

        bar.photos.add(R.drawable.img_jackies0);
        bar.photos.add(R.drawable.img_jackies1);

        bar.livePhotos.add(R.drawable.img_jackies2);
        bar.livePhotos.add(R.drawable.img_jackies3);

        barMap.put(bar.barName,bar);
        //End Jackies

        //Moes Barbecue
        bar = new Bar();
        bar.barName = "Moe's Original BBQ";
        bar.coverOver = "$5";
        bar.phone = "(205) 752-3616";
        bar.address = "2101 University Blvd, Tuscaloosa, AL 35401";
        bar.description = "Easygoing chain serving Alabama-style pulled pork & other meats smoked in-house.  Fun and chill bar";
        bar.tags.add("Bars with Food");
        bar.tags.add("Outdoor Venues");
        bar.tags.add("All Bars");
        bar.litness = "3";
        bar.rating = "4.1";

        today = new Day();
        today.day = "Monday";
        today.events.add("Moe's Monday");
        today.events.add("Bingo");
        today.specials.add("50c Wings");
        today.specials.add("$10 Beer Towers");
        bar.days.add(today);

        today = new Day();
        today.day = "Tuesday";
        today.events.add("Tequila Tuesday");
        today.specials.add("$1 Tequila Shots");
        bar.days.add(today);

        today = new Day();
        today.day = "Thursday";
        today.specials.add("1/2 priced Bushwackers");
        bar.days.add(today);

        bar.menu.drinks.add(new Item("Single Wells", 6));
        bar.menu.drinks.add(new Item("Bud Light", 3));
        bar.menu.drinks.add(new Item("Miller Light", 3));
        bar.menu.drinks.add(new Item("Clearwater Shots", 2));
        bar.menu.drinks.add(new Item("Bushwacker", 7));

        bar.menu.food.add(new Item("Pulled Pork", 10));
        bar.menu.food.add(new Item("Pulled Chicken", 9));
        bar.menu.food.add(new Item("Wing Plate", 10));
        bar.menu.food.add(new Item("Brisket", 12));

        review = new Review();
        review.rating = "5";
        review.text = "Best wings in town!  The bushwackers are amazing too";
        review.user = "Mason Goomes";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "4";
        review.text = "Good bar for a chill night out with the boys";
        review.user = "Jake Groeber";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "3";
        review.text = "There was no dancing";
        review.user = "Mike Norman";
        bar.reviews.add(review);

        bar.livePhotos.add(R.drawable.img_moes0);
        bar.livePhotos.add(R.drawable.img_moes1);
        bar.livePhotos.add(R.drawable.img_moes2);

        bar.photos.add(R.drawable.img_moes3);
        bar.photos.add(R.drawable.img_moes4);

        barMap.put(bar.barName,bar);
        //Moes Barbecue

        //Wheelhouse
        bar = new Bar();
        bar.barName = "The Wheelhouse Pub";
        bar.phone = "(205) 535-5933";
        bar.address = "2326 4th St, Tuscaloosa, AL 35401";
        bar.description = "Fun bar with pool tables, darts, ski ball, basketball, and more.";
        bar.tags.add("Bars with Food");
        bar.tags.add("All Bars");
        bar.litness = "2";
        bar.rating = "3.7";

        today = new Day();
        today.day = "Friday";
        today.events.add("Live Band");
        bar.days.add(today);

        bar.menu.drinks.add(new Item("Domestics", 4));
        bar.menu.drinks.add(new Item("Imports", 5));
        bar.menu.drinks.add(new Item("Wells", 5));
        bar.menu.drinks.add(new Item("Calls", 6));

        bar.menu.food.add(new Item("Pizza", 5));
        bar.menu.food.add(new Item("Smoked Chicken", 9));
        bar.menu.food.add(new Item("Chicken Bacon Ranch", 8));
        bar.menu.food.add(new Item("Wings Small", 8));
        bar.menu.food.add(new Item("Wings Large", 13));

        review = new Review();
        review.rating = "3";
        review.text = "Love all the fun games but not much going on besides that";
        review.user = "Amari Cooper";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "5";
        review.text = "Amari is just mad I can punch harder than him according the punching bag game.  Love this place";
        review.user = "Damien Harris";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "3";
        review.text = "Just an average bar in Tuscaloosa";
        review.user = "Tua Tag";
        bar.reviews.add(review);

        bar.photos.add(R.drawable.img_wheelhouse0);
        bar.photos.add(R.drawable.img_wheelhouse1);
        bar.photos.add(R.drawable.img_wheelhouse2);

        bar.livePhotos.add(R.drawable.img_wheelhouse4);

        barMap.put(bar.barName,bar);
        //End Wheelhouse

        //Rhythm and Brews
        bar = new Bar();
        bar.barName = "Rhythm & Brews";
        bar.coverOver = "$5";
        bar.wait = "5 Minutes";
        bar.phone = "(205) 248-7181";
        bar.address = "2308 4th St, Tuscaloosa, AL 35401";
        bar.description = "Great bar for dancing and watching live bands.  Also has a mechanical bull";
        bar.tags.add("All Bars");
        bar.litness = "3";
        bar.rating = "3.9";

        today = new Day();
        today.day = "Wednesday";
        today.events.add("Live Band");
        today.specials.add("$4 Wells");
        bar.days.add(today);

        today = new Day();
        today.day = "Saturday";
        today.events.add("Live Band");
        today.specials.add("$3 Bud Lights");
        bar.days.add(today);

        bar.menu.drinks.add(new Item("Single Wells", 6));
        bar.menu.drinks.add(new Item("Bud Light", 4));
        bar.menu.drinks.add(new Item("Miller Light", 4));
        bar.menu.drinks.add(new Item("Clearwater Shots", 2));

        review = new Review();
        review.rating = "4";
        review.text = "Always fun watching friends ride the bull. Fun place to swing dance";
        review.user = "Peyton Manning";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "4";
        review.text = "Love the band they had playing when I went.  Sweet Home Alabama is always a banger";
        review.user = "Joe Nammath";
        bar.reviews.add(review);

        review = new Review();
        review.rating = "3";
        review.text = "Not really a swing dance type of guy, but it was still fun to listen to music.";
        review.user = "Tom Brady";
        bar.reviews.add(review);

        bar.photos.add(R.drawable.img_rhythm0);
        bar.photos.add(R.drawable.img_rhythm1);
        bar.photos.add(R.drawable.img_rhythm2);

        bar.livePhotos.add(R.drawable.img_rhythm3);
        bar.livePhotos.add(R.drawable.img_rhythm4);

        barMap.put(bar.barName,bar);
        //End Rhythm and Brews


        bar = new Bar();
        bar.barName = "Pattys Pub";
        bar.photos = defPhotos;
        bar.livePhotos = defLive;
        bar.days = defDay;
        bar.menu = defMenu;
        bar.reviews = defReviews;
        bar.litness = "5";
        bar.tags.add("All Bars");
        bar.tags.add("Bars with Food");
        bar.description = "This is the bar from the famous TV Show It's Always Sunny in Philadelphia";
        bar.coverOver = "$5";
        bar.coverUnder = "$10";
        bar.wait = "25 Minutes";
        barMap.put(bar.barName,bar);

        bar = new Bar();
        bar.barName = "Original Heron Bistro";
        bar.photos = defPhotos;
        bar.livePhotos = defLive;
        bar.days = defDay;
        bar.menu = defMenu;
        bar.reviews = defReviews;
        bar.litness = "3";
        bar.tags.add("All Bars");
        bar.tags.add("Under 21");
        bar.wait = "10 Minutes";
        barMap.put(bar.barName,bar);

        bar = new Bar();
        bar.barName = "Naughty Outlaw Beer Garden";
        bar.photos = defPhotos;
        bar.livePhotos = defLive;
        bar.days = defDay;
        bar.menu = defMenu;
        bar.reviews = defReviews;
        bar.tags.add("All Bars");
        bar.tags.add("Breweries");
        barMap.put(bar.barName,bar);

        bar = new Bar();
        bar.barName = "Leaning Bicycle Brewpub";
        bar.photos = defPhotos;
        bar.livePhotos = defLive;
        bar.days = defDay;
        bar.menu = defMenu;
        bar.reviews = defReviews;
        bar.litness = "5";
        bar.tags.add("All Bars");
        bar.tags.add("Breweries");
        bar.tags.add("Outdoor Venues");
        bar.coverOver = "$5";
        barMap.put(bar.barName,bar);

        bar = new Bar();
        bar.barName = "Mighty Sidecar Speakeasy";
        bar.photos = defPhotos;
        bar.livePhotos = defLive;
        bar.days = defDay;
        bar.menu = defMenu;
        bar.reviews = defReviews;
        bar.litness = "3";
        bar.tags.add("All Bars");
        bar.tags.add("Night Clubs");
        bar.coverOver = "$15";
        barMap.put(bar.barName,bar);

        bar = new Bar();
        bar.barName = "Crystal Tiger Brewhouse";
        bar.photos = defPhotos;
        bar.livePhotos = defLive;
        bar.days = defDay;
        bar.menu = defMenu;
        bar.reviews = defReviews;
        bar.litness = "4";
        bar.tags.add("All Bars");
        bar.tags.add("Outdoor Venues");
        bar.tags.add("Bars with Food");
        bar.tags.add("Under 21");
        barMap.put(bar.barName,bar);

        bar = new Bar();
        bar.barName = "Little Vibes Brewhouse";
        bar.photos = defPhotos;
        bar.livePhotos = defLive;
        bar.days = defDay;
        bar.menu = defMenu;
        bar.reviews = defReviews;
        bar.litness = "3";
        bar.tags.add("All Bars");
        bar.tags.add("Outdoor Venues");
        bar.tags.add("Bars with Food");
        bar.tags.add("Under 21");
        barMap.put(bar.barName,bar);

        bar = new Bar();
        bar.barName = "Hazy Bicycle Brewpub";
        bar.photos = defPhotos;
        bar.livePhotos = defLive;
        bar.days = defDay;
        bar.menu = defMenu;
        bar.reviews = defReviews;
        bar.litness = "3";
        bar.tags.add("All Bars");
        bar.tags.add("Bars with Food");
        bar.tags.add("Under 21");
        bar.wait = "15 Minutes";
        barMap.put(bar.barName,bar);

        bar = new Bar();
        bar.barName = "Lazy Hedgehog Roadhouse";
        bar.photos = defPhotos;
        bar.livePhotos = defLive;
        bar.days = defDay;
        bar.menu = defMenu;
        bar.reviews = defReviews;
        bar.litness = "2";
        bar.coverUnder = "$15";
        bar.tags.add("All Bars");
        bar.tags.add("Night Clubs");
        bar.tags.add("Bars with Food");
        bar.tags.add("Under 21");
        barMap.put(bar.barName,bar);
    }

    private void launchFirstActivity() {
        if (preferenceFileExist())
            getLogin();
        //if it doesn't exist you can keep the client username as null
        else
            startActivity(new Intent(this,MainActivity.class));

    }

    private boolean preferenceFileExist() {
        return new File(getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/Login.xml").exists();
    }

    private void getLogin() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Login", MODE_PRIVATE);
        String username = sharedPreferences.getString("Unm",null);
        String password = sharedPreferences.getString("Psw",null);
        checkLogin(username,password);
    }

    private void checkLogin(String username, String password) {
        //if the login is null
        if(username == null && password == null) {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        else {
            //set the currentUsername
            Client.currentUserName = username;
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
