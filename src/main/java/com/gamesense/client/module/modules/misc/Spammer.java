package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.api.util.misc.Timer;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import java.util.Arrays;
import java.util.Random;

@Module.Declaration(name = "Spammer", category = Category.Misc)
public class Spammer extends Module {
	
	ModeSetting mode = registerMode("Mode", Arrays.asList("Toxic", "Advertise", "AntiRacist", "Clients"), "Toxic");
	IntegerSetting minDelay = registerInteger("MinDelay", 1, 0, 30);
	IntegerSetting maxDelay = registerInteger("MaxDelay", 10, 0, 30);
	
	private static final Random RNG = new Random();
	private final Timer timer = new Timer();
	
	public void onDisable() {
		timer.reset();
	}
	
	public void OnUpdate() {
        if (timer.passedS(random(minDelay.getValue(), maxDelay.getValue()))) {
            timer.reset();
            
            int messagesToxic = random(1, 26);
            int messagesAdvertise = random(1, 16);
            int messagesAntiRacist = random(1, 14);
            int messagesClients = random(1, 19);
            
            switch(mode.getValue()) {
            case "Toxic": {
            	switch(messagesToxic) {
            	case 1: {
            		mc.player.sendChatMessage("> my richness powered by spidersense");
                break;
            	}
            	case 2: {
            		mc.player.sendChatMessage("> im doing your mom rn no cap");
                break;
            	}
            	case 3: {
            		mc.player.sendChatMessage("> stfu pooron");
                break;
            	}
            	case 4: {
            		mc.player.sendChatMessage("> LOL COPE MORE KID");
                break;
            	}
            	case 5: {
            		mc.player.sendChatMessage("> say one more word and your getting swatted faggot");
                break;
            	}
            	case 6: {
            		mc.player.sendChatMessage("> the iq of some people on this server is so low it's astonishing");
                break;
            	}
            	case 7: {
            		mc.player.sendChatMessage("> shut your little cumhole retard, im richer than you thanks to spidersense");
                break;
            	}
            	case 8: {
            		mc.player.sendChatMessage("> compare funds rn so poor lmfao");
                break;
            	}
            	case 9: {
            		mc.player.sendChatMessage("> keep using future little cunt fanboy");
                break;
            	}
            	case 10: {
            		mc.player.sendChatMessage("> LOL the shittalker is getting SWATTED in vc join now!");
                break;
            	}
            	case 11: {
            		mc.player.sendChatMessage("> go craw back into mummy's pussy little incel");
                break;
            	}
            	case 12: {
            		mc.player.sendChatMessage("> the dox squad is after you bro!");
                break;
            	}
            	case 13: {
            		mc.player.sendChatMessage("> your iq is below room tempature! that's cold bro");
                break;
            	}
            	case 14: {
            		mc.player.sendChatMessage("> imagine being bad, it's just something i can't relate to");
                break;
            	}
            	case 15: {
            		mc.player.sendChatMessage("> BRO YOUR WINDOW IS ABOUT TO GET SMASHED BY THE II GOON SQUAD!!!!");
                break;
            	}
            	case 16: {
            		mc.player.sendChatMessage("> ran impact? i backdoored it LOOLLLLOLLOOLOLOL");
                break;
            	}
            	case 17: {
            		mc.player.sendChatMessage("> my crystal will crawl right up your ass shitter");
                break;
            	}
            	case 18: {
            		mc.player.sendChatMessage("> XDDD the 11 year old is crying in vc cuz he got swatted LMFAOOOO");
                break;
            	}
            	case 19: {
            		mc.player.sendChatMessage("> the goon squad will roll up to your door if you don't shut the fuck up pooron");
                break;
            	}
            	case 20: {
            		mc.player.sendChatMessage("> I OWN U KID");
                break;
            	}
            	case 21: {
            		mc.player.sendChatMessage("> i will fuck your ass so hard in cpvp your ass will hurr till' your dead LOL");
                break;
            	}
            	case 22: {
            		mc.player.sendChatMessage("> bro he's getting doxxed, HES GETTING DOXXED!!!");
                break;
            	}
            	case 23: {
            		mc.player.sendChatMessage("> look behind you faggot");
                break;
            	}
            	case 24: {
            		mc.player.sendChatMessage("> IMAGINE LOSING TO TRANNIE HAHAHAHAAHAHAHA");
                break;
            	}
            	case 25: {
            		mc.player.sendChatMessage("> learn to cope and seethe jesus");
                break;
            	}
            	case 26: {
            		mc.player.sendChatMessage("my coolness powered by hausemasterissue and his epic client");
                break;
            	}
            			
            		
            	}
            }
            
            case "Advertise": {
            	switch(messagesAdvertise) {
            	case 1: {
            		mc.player.sendChatMessage("> spidersense isn't a rat dumbass");
                break;
            	}
            	case 2: {
            		mc.player.sendChatMessage("> stop using 0xbackdoor (aka future), use spidersense");
                break;
            	}
            	case 3: {
            		mc.player.sendChatMessage("> HOLY SHIT i can't stop winning thanks to spidersense");
                break;
            	}
            	case 4: {
            		mc.player.sendChatMessage("> bro imagine using gamesense, spidersense is on top");
                break;
            	}
            	case 5: {
            		mc.player.sendChatMessage("> BRO THE CA IS SO FAST IN SPIDERSENSE WTF");
                break;
            	}
            	case 6: {
            		mc.player.sendChatMessage("> the spidersense dev deobfuscated future lol");
                break;
            	}
            	case 7: {
            		mc.player.sendChatMessage("> hausemasterissue is on top baby! u mad newfag? u mad faggot?");
                break;
            	}
            	case 8: {
            		mc.player.sendChatMessage("> thanks to spidersense im staying WIDE baby WIDE");
                break;
            	}
            	case 9: {
            		mc.player.sendChatMessage("> spidersense owns me and all");
                break;
            	}
            	case 10: {
            		mc.player.sendChatMessage("> spidersense is free, open source, and SAFE! check the src yourself!");
                break;
            	}
            	case 11: {
            		mc.player.sendChatMessage("> download spidersense FOR FREE at github.com/hausemasterissue/spidersense");
                break;
            	}
            	case 12: {
            		mc.player.sendChatMessage("> the dev of spidersense added more updates in 2 weeks than future get's in a WHOLE YEAR LOOL");
                break;
            	}
            	case 13: {
            		mc.player.sendChatMessage("> spidersense is 100% not skidded");
                break;
            	}
            	case 14: {
            		mc.player.sendChatMessage("> spidersense totally never pasted any code from cousinware, cosmos, momentum, legacy, future, or oyvey!");
                break;
            	}
            	case 16: {
            		mc.player.sendChatMessage("> HAUSEMASTERISSUE IS BASED BEYOND ANYTHING TO EVER EXIST");
                break;
            	}
            	}
            }
            
            case "AntiRacist": {
            	switch(messagesAntiRacist) {
            	case 1: {
            		mc.player.sendChatMessage("> bro imagine being racist in 2021, cringe");
                break;
            	}
            	case 2: {
            		mc.player.sendChatMessage("> WE ARE ALL EQUAL");
                break;
            	}
            	case 3: {
            		mc.player.sendChatMessage("> Do not judge on color, judge on personality.");
                break;
            	}
            	case 4: {
            		mc.player.sendChatMessage("> #BLM");
                break;
            	}
            	case 5: {
            		mc.player.sendChatMessage("> Color TV was meant for white people, together we can make it for all skin colors!");
                break;
            	}
            	case 6: {
            		mc.player.sendChatMessage("> SUPPORT BLM");
                break;
            	}
            	case 7: {
            		mc.player.sendChatMessage("> Stop being racist, be kind");
                break;
            	}
            	case 8: {
            		mc.player.sendChatMessage("> END WHITE SUPREMACY");
                break;
            	}
            	case 9: {
            		mc.player.sendChatMessage("> Wao, PopBob, FitMC and SalC1 all remind you to support BLM!");
                break;
            	}
            	case 10: {
            		mc.player.sendChatMessage("> Want to end racism? Vote Obama 2024");
                break;
            	}
            	case 11: {
            		mc.player.sendChatMessage("> END RACSIM, JOIN THE PUNXATAWNEY GROUNDHOG CLUB");
                break;
            	}
            	case 12: {
            		mc.player.sendChatMessage("> Help end segregation in South Africa, watch KiLAB Gaming on YT");
                break;
            	}
            	case 13: {
            		mc.player.sendChatMessage("> Black or white, love is love.");
                break;
            	}
            	case 14: {
            		mc.player.sendChatMessage("> HausemasterIssue and SpiderSense client support BLM!");
                break;
            	}
                }
            }
            
            case "Clients": {
            	switch(messagesClients) {
            	case 1: {
            		mc.player.sendChatMessage("> Imagine using Wurst+2 in 2021, Cringe");
                break;
            	}
            	case 2: {
            		mc.player.sendChatMessage("> Don't buy Pyro, it's just a SalHack skid");
                break;
            	}
            	case 3: {
            		mc.player.sendChatMessage("> SalHack in 2021? Really?");
                break;
            	}
            	case 4: {
            		mc.player.sendChatMessage("> The developers of Future are cunts, don't give them your $20");
                break;
            	}
            	case 5: {
            		mc.player.sendChatMessage("> John is based. Go buy his insane client at rusherhack.org");
                break;
            	}
            	case 6: {
            		mc.player.sendChatMessage("> Inertia is bad, stop using it");
                break;
            	}
            	case 7: {
            		mc.player.sendChatMessage("> Why are you using phobos when you aren't playing on cc? Get real");
                break;
            	}
            	case 8: {
            		mc.player.sendChatMessage("> Travis is sexy, so is MrWorldWide, go use his chad client Wurst+3");
                break;
            	}
            	case 9: {
            		mc.player.sendChatMessage("> Please for the love of god, STOP USING KAMI BLUE, FUCK KAMI BLUE!");
                break;
            	}
            	case 10: {
            		mc.player.sendChatMessage("> Lambda is coded in Kotin, what a bunch of weirdos");
                break;
            	}
            	case 11: {
            		mc.player.sendChatMessage("> Inferno is so good, Sxmurai did an amazing job");
                break;
            	}
            	case 12: {
            		mc.player.sendChatMessage("> GameSense is discontinued, what a tragedy");
                break;
            	}
            	case 13: {
            		mc.player.sendChatMessage("> Konas is so dogshit, it's been cracked by the fellas at PlutoSolutions for free, don't pay $15 for it");
                break;
            	}
            	case 14: {
            		mc.player.sendChatMessage("> #Gondal.clubNeverForget");
                break;
            	}
            	case 15: {
            		mc.player.sendChatMessage("> RenoSense is a RAT, it has been confirmed");
                break;
            	}
            	case 16: {
            		mc.player.sendChatMessage("> Abyss get's worse by every version, it's just sad at this point");
                break;
            	}
            	case 17: {
            		mc.player.sendChatMessage("> Catalyst.sexy is not sexy at all");
                break;
            	}
            	case 18: {
            		mc.player.sendChatMessage("> Impact is a RAT, wipe your whole PC right away!");
                break;
            	}
            	case 19: {
            		mc.player.sendChatMessage("> SpiderSense is the best client in existence");
                break;
            	}
            	}
            }
            
            }      
            
        }
    }
	
	private int random(int min, int max) {
        return RNG.nextInt(max + min) - min;
    }
	
    
}
