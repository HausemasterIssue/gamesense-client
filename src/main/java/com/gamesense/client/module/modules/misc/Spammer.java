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
* @since 6/11/2021
* i had wayyy to much fun with this lol
*/


@Module.Declaration(name = "Spammer", category = Category.Misc)
public class Spammer extends Module {
  
  ModeSetting mode = registerMode("Mode", Arrays.asList("Toxic", "Advertise", "AntiRacist", "Clients"), "Toxic");
  IntegerSetting delaySeconds = registerInteger("Delay", 5, 0, 180);
  
  private int delay = 0;
  
  List msgsToxic = Arrays.asList("the 11 year old is getting swatted in vc LOL!", "my richness powered by spidersense", 
                            "compare funds rn LOL!", "getting ur ip rn, gimme 1 sec", "pooron roflsauce", 
                            "im the KING rofl", "im richer than your entire bloodline nn", "SO POOR LOOOL",
                            "say one more world and im leaking ur dox LOL", "the irl goon squad is en route faggot",
                            "you probably live in a mud hut roflsauce", "cope more random", "stay mad kid LEL",
                            "nobodys talking cuz they know thats a SWAT LMAOO", "speak up nn, i couldnt hear your nn talk", 
                            "POP MORE ROFLLL", "i have your dox LOL", "shut up before you get a SWAT LOOOLOLOL",
                            "guys hes bri'ish LMFAOOO", "look out bro the ii goon squad is at your window!!!",
                            "spidersense owns me and all LOL", "fundless faggot LEL!");
  List msgsAdvertise = Arrays.asList("SpiderSense owns me and all!", "The CA in SpiderSense is too fast bro!",
                                     "HausemasterIssue and his shitty client are so based!", "OMFG HOW IS SPIDERSENSE THIS GOOD???", 
                                     "SpiderSense makes me 500% better at CPVP!", "I just gained like, 3 000 000 funds from SpiderSense!", 
                                     "SpiderSense is one of the BEST clients out there right now", "Bro your not using SpiderSense? Cringe.",
                                     "RusherWHO? SpiderSense is on top!", "Switch to SpiderSense right now, it's amazing",
                                     "SpiderSense is so good bro, IDK why more people don't use it", "People just seem worse ever since I got SpiderSense",
                                     "TickShift and BowBomb are in SpiderSense!", "The crystal aura is so good on strict, like 10/10",
                                     "I went from being a peasnt to a KING with SpiderSense!", "I gained 67 IQ points from SpiderSense!",
                                     "SpiderSense is free, open source, and SAFE!", "github.com/hausemasterissue/spidersense 10/10 client");
  List msgsAntiRacist = Arrays.asList("BLACK LIVES MATTER!", "HAVE YOU POSTED YOUR BLACK SQUARE?", "#BLM",
                                      "Racism is so cringe in 2021 bro", "Everyone is equal!", "WAO Supports BLM!", 
                                      "HE IS RACIST!", "No more racism!", "Skin colors do not mean different people!",
                                      "END WHITE SUPREMACY!", "There are going to be more whites than blacks in 2050!",
                                      "Vote Obama 2024!", "Please, stop racism.", "Our fathers were racists, but WE WILL NOT BE!",
                                      "Like living? Support BLM!", "FitMC, SalC1, Armorsmith and HausemasterIssue proudly support BLM!",
                                      "Like being based like Travis? Support BLM TODAY!", "Stopping racism gives you +250 Social credit!",
                                      "Asian, Black, Hispanic, or white, WE ARE ALL EQUAL!", "END RACISM! JOIN EMPERIUM!");
  List msgsClients = Arrays.asList("Go kiss 0x22's ass more Future fag!", "Konas SRC was publicly leaked LOL!", "SalHack in 2021? Wow, you ARE a newfag",
                                   "Wurst+ will always be the KING of all clients!", "Phobos? Never heard of it.", "FaxHax is real.", 
                                   "RusherHack is good, go buy it at rusherhack.org", "Oyvey? Reconsider where your life is going please.",
                                   "Abyss is an exit scam, you just wasted $20 ROFL", "Pyro = Salhack, sorry to break it to you.",
                                   "Catalyst is pasted from Gishmod, and you paid $20 for it LEL!", "FencingF+2 is kinda hot ngl",
                                   "Using one of QQ's cracks again? Enjoy the rat kid.", "You wasted $20 on 0xbackdoor- I meant Future client faggot",
                                   "Stop skidding Oyvey, FOR FIVE SECONDS!", "RenoSense is a confirmed RAT, go wipe your PC STAT!",
                                   "Yoink was ratted by his own rat once, how tragic.", "Literal apes use zopac's clients roflsauce", 
                                   "Still using Inertia? Wow, that's sad.", "Nutgod.cc is so good", "LeuxBackdoor? You are a literal fucking monkey",
                                   "Hyperlethal is just Wurst+2", "Impact is a RAT I repeat IMPACT is a RAT!", "Meteor? Take a toaster bath please.",
                                   "SPIDERSENSE IS AT THE TIPPITY TOP OF ALL CLIENTS!");
  
  Random rToxic = new Random();
  int randomitemToxic = rToxic.nextInt(msgsToxic.size());
  String randomElementToxic = (String) msgsToxic.get(randomitemToxic);
  Random rAd = new Random();
  int randomitemAd = rAd.nextInt(msgsAdvertise.size());
  String randomElementAd = (String) msgsAdvertise.get(randomitemAd);
  Random rRacist = new Random();
  int randomitemRacist = rRacist.nextInt(msgsAntiRacist.size());
  String randomElementRacist = (String) msgsAntiRacist.get(randomitemRacist);
  Random rClients = new Random();
  int randomitemClients = rClients.nextInt(msgsClients.size());
  String randomElementClients = (String) msgsClients.get(randomitemClients);
  
  
  public void onUpdate() {
    delay++;
    if (this.delay > (Integer)this.delaySeconds.getValue() * 40) {
        if(mode.getValue().equalsIgnoreCase("Toxic")) {
            mc.player.sendChatMessage(randomElementToxic);
        } else if (mode.getValue().equalsignoreCase("Advertise")) {
            mc.player.sendChatMessage(randomElementAd);
        } else if (mode.getValue().equalsIgnoreCase("AntiRacist")) {
            mc.player.sendChatMessage(randomElementRacist);
        } else if (mode.getValue().equalsignoreCase("Clients")) {
            mc.player.sendChatMessage(randomElementClients);
        }
      
        delay = 0;
        
    }
    
    
  }
  

}
