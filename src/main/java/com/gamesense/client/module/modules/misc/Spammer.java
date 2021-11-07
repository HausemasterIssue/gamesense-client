package com.gamesense.client.module.modules.combat;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.setting.values.ModeSetting;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
* @author hausemasterissue
* @since 7/11/2021
* i had wayyy to much fun with this lol
*/


@Module.Declaration(name = "Spammer", category = Category.Misc)
public class Spammer extends Module {
  
  ModeSetting mode = registerMode("Mode", Arrays.asList("Toxic", "Advertise", "AntiRacist", "Clients", "JaredVPopBob", "Motivation"), "Toxic");
  IntegerSetting delaySeconds = registerInteger("Delay", 5, 0, 180);
  
  private int delay = 0;
  private int funnePlace = -1;
  private String[] mToxic = {"the 11 year old is getting swatted in vc LOL!", "my richness powered by spidersense", 
                            "compare funds rn LOL!", "getting ur ip rn, gimme 1 sec", "pooron roflsauce", 
                            "im the KING rofl", "im richer than your entire bloodline nn", "SO POOR LOOOL",
                            "say one more world and im leaking ur dox LOL", "the irl goon squad is en route faggot",
                            "you probably live in a mud hut roflsauce", "cope more random", "stay mad kid LEL",
                            "nobodys talking cuz they know thats a SWAT LMAOO", "speak up nn, i couldnt hear your nn talk", 
                            "POP MORE ROFLLL", "i have your dox LOL", "shut up before you get a SWAT LOOOLOLOL",
                            "guys hes bri'ish LMFAOOO", "look out bro the ii goon squad is at your window!!!",
                            "spidersense owns me and all LOL", "fundless faggot LEL!", "i told the gang your adress nigga",
                            "GANG GANG", "my dogs wil rip you into pieces LOOOOOL", "bro your so fucking fat LELELEL!!!",
                            "im more famous than your all your ancestors COMBINED!", "your iq is lower than room temp lol",
                            "check the bin nigga"};
  private String[] mAd = {"SpiderSense owns me and all!", "The CA in SpiderSense is too fast bro!",
                                     "HausemasterIssue and his shitty client are so based!", "OMFG HOW IS SPIDERSENSE THIS GOOD???", 
                                     "SpiderSense makes me 500% better at CPVP!", "I just gained like, 3 000 000 funds from SpiderSense!", 
                                     "SpiderSense is one of the BEST clients out there right now", "Bro your not using SpiderSense? Cringe.",
                                     "RusherWHO? SpiderSense is on top!", "Switch to SpiderSense right now, it's amazing",
                                     "SpiderSense is so good bro, IDK why more people don't use it", "People just seem worse ever since I got SpiderSense",
                                     "TickShift and BowBomb are in SpiderSense!", "The crystal aura is so good on strict, like 10/10",
                                     "I went from being a peasnt to a KING with SpiderSense!", "I gained 67 IQ points from SpiderSense!",
                                     "SpiderSense is free, open source, and SAFE!", "github.com/hausemasterissue/spidersense 10/10 client"};
  private String[] mRacist = {"BLACK LIVES MATTER!", "HAVE YOU POSTED YOUR BLACK SQUARE?", "#BLM",
                                      "Racism is so cringe in 2021 bro", "Everyone is equal!", "WAO Supports BLM!", 
                                      "HE IS RACIST!", "No more racism!", "Skin colors do not mean different people!",
                                      "END WHITE SUPREMACY!", "There are going to be more whites than blacks in 2050!",
                                      "Vote Obama 2024!", "Please, stop racism.", "Our fathers were racists, but WE WILL NOT BE!",
                                      "Like living? Support BLM!", "FitMC, SalC1, Armorsmith and HausemasterIssue proudly support BLM!",
                                      "Like being based like Travis? Support BLM TODAY!", "Stopping racism gives you +250 Social credit!",
                                      "Asian, Black, Hispanic, or white, WE ARE ALL EQUAL!", "END RACISM! JOIN EMPERIUM!"};
  private String[] mClients = {"Go kiss 0x22's ass more Future fag!", "Konas SRC was publicly leaked LOL!", "SalHack in 2021? Wow, you ARE a newfag",
                                   "Wurst+ will always be the KING of all clients!", "Phobos? Never heard of it.", "FaxHax is real.", 
                                   "RusherHack is good, go buy it at rusherhack.org", "Oyvey? Reconsider where your life is going please.",
                                   "Abyss is an exit scam, you just wasted $20 ROFL", "Pyro = Salhack, sorry to break it to you.",
                                   "Catalyst is pasted from Gishmod, and you paid $20 for it LEL!", "FencingF+2 is kinda hot ngl",
                                   "Using one of QQ's cracks again? Enjoy the rat kid.", "You wasted $20 on 0xbackdoor- I meant Future client faggot",
                                   "Stop skidding Oyvey, FOR FIVE SECONDS!", "RenoSense is a confirmed RAT, go wipe your PC STAT!",
                                   "Yoink was ratted by his own rat once, how tragic.", "Literal apes use zopac's clients roflsauce", 
                                   "Still using Inertia? Wow, that's sad.", "Nutgod.cc is so good", "LeuxBackdoor? You are a literal fucking monkey",
                                   "Hyperlethal is just Wurst+2", "Impact is a RAT I repeat IMPACT is a RAT!", "Meteor? Take a toaster bath please.",
                                   "SPIDERSENSE IS AT THE TIPPITY TOP OF ALL CLIENTS!"};
  // lol yes i legitmatley listenened to the video and wrote down all the lines
  private String[] mFun = {"Alright, the following logs are from December 25 2015 on 2b2t", "I'm going to attempt to recapture the magic of this moment",
                          "Now there is over 900 lines of chat", "But I've condesned it down a little shorter", "It starts out with popbob saying \"I'm out of alchohol\"",
                          "To which Jared responds, \"Pop, you can drink my semen\"", "Popbob reponds, \"Shut the fuck up\"", "Jared using green text \"Driving your 20 year old car, can't afford a new one because booze money\"",
                          "Popbob exlaims that Jared is triggered", "To which jared responds, \"Pop, your obiese\"", "To which pop responds \"You fat FUCK!\"", 
                          "Jared exlaims \"Because your parents drive used shit from 1970\"", "Popbob once again, \"Lose some weight\"", 
                          "Jared responds, \"Pop, what's it like being 500 pounds over the national average?\"", "Popbob says \"Stay mad Jared Miller\"",
                          "Jared says \"Stay mad Alan Schafer\"", "Popbob responds \"Yeah your name is miller because your parents were lamen, fucking loser!",
                          "Your so fat your ancesters are losers too LMAO!\"", "No this continues for a few more minutes, but climaxes with Jared exlaiming: ",
                          "\"Go sniff paint you braindamaged kike\"", "Popbob responds, \"I will fuck your ass fag\"", "Jared says, \"Not if I fuck yours first!\"",
                          "To which popbob responds, \"Your too weak willed and feminine for that\"", "I want you to let that sink in for a moment", 
                           "Now the rest of it, if you want to read through it go right ahead", "But I've just condensed the more colorful quotes for you"
                           "\"Go play in traffic\"", "\"Your family should abanden you\"", "\"Go pray to God faggot\"", "\"Pop, your the result of countless generations of inbreeding\"", 
                           "\"Your more autsitic that Chris Chan\"", "\"He's going to autism himself into an autism white drwarf, he will then eventually collapse into an assburgers blackhole\"",
                           "\"Pop your too scared to even fight me at IMPS\"", "\"Everyone knows about your fetish for cross dressing Jared\"", 
                           "\"Jared is 16 years old, he's still in high school, born in 1999, he is fat. as. fuck.\"", "Now if you would like to read the entirity of this exchange I will leave a link below."};
  public String[] mMotiv = {"Others will stop playing after a few minutes, we will play until we are known!", "Normal people get mad, we get happy!",
                           "Randoms will stay male, we will become trans!", "Others will be humble, we will have huge egos!", 
                           "Many quit because of the queue, but we live for the queue!", "Normal people will beg, we earn.",
                           "Noname's will judge on personality, we will judge on namemc views!", "Most get mad at hackers, we praise hackers!",
                           "Others will die once and say it was lag, we will die and say it was desync!", "Normal people will cry once they hear us, we will gleam in joy!",
                           "Many will quit after losing all their gear, but we use it to steal more gear!", "Noname's will stay mad, we will stay joyful",
                           "Others will practice for an hour a day, we practice for a month a day!", "To others, it's a game, to us it is our existence!",
                           "Many will report after being called the n-word, we will give them gifts for saying it!", "Randoms will leave after dying in a second, we keep playing.",
                           "Others think swearing is bad, we think of it as a great thing!", "Many will think hacking is bad, but to us, it is a way of life.",
                           "Non's see popbob and scream, we see popbob and cum!", "Noname's will respect women, we will fuck them!",
                           "Many will cry after playing for hours, we will find that persons dox!", "Others have little kid voices, we have female voices!",
                           "Many shit themselves after someone knows their adress, we welcome them.", "Randoms will think Future is good, we will think SpiderSense is great!",
                           "Others will like democracy, we will love communism!"};

  public void onUpdate() {
    Random rToxic = new Random();
    int randomToxic = rToxic.nextInt(mToxic.length);
    Random rAd = new Random();
    int randomAd = rAd.nextInt(mAd.length);
    Random rRacist = new Random();
    int randomRacist = rRacist.nextInt(mRacist.length);
    Random rClients = new Random();
    int randomClients = rClients.nextInt(mClients.length);
    Random rMotiv = new Random();
    int randomMotiv = rMotiv.nextInt(mMotiv.length);
    delay++;
    funnePlace++;
    if (delay > (Integer) delaySeconds.getValue() * 20) {
        if(mode.getValue().equalsIgnoreCase("Toxic")) {
            mc.player.sendChatMessage(mToxic[randomToxic]);
            funnePlace = 0;  
        } else if (mode.getValue().equalsIgnoreCase("Advertise")) {
            mc.player.sendChatMessage(mAd[randomAd]);
            funnePlace = 0;
        } else if (mode.getValue().equalsIgnoreCase("AntiRacist")) {
            mc.player.sendChatMessage(mRacist[randomRacist]);
            funnePlace = 0;
        } else if (mode.getValue().equalsIgnoreCase("Clients")) {
            mc.player.sendChatMessage(mClients[randomClients]);
            funnePlace = 0;
        } else if (mode.getValue().equalsIgnoreCase("JaredVPopBob")) {
            mc.player.sendChatMessage(mFun[funnePlace]); 
        } else if (mode.getValue().equalsIgnoreCase("Motivation")) {
            mc.player.sendChatMessage(mMotiv[randomMotiv]);
            funnePlace = 0;
        }
      
        delay = 0;
        
    }
    
    
  }
  

}
