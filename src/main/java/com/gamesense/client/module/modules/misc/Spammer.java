package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.api.util.misc.MessageBus;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import java.util.Arrays;
import java.util.Random;

/*
* @author hausemasterissue
* @since 14/10/2021
*/

@Module.Declaration(name = "Spammer", category = Category.Misc)
public class Spammer extends Module {

	ModeSetting mode = registerMode("Mode", Arrays.asList("Toxic", "Advertise", "AntiRacist", "Clients"), "Toxic");
    IntegerSetting minDelay = registerInteger("Min Delay", 5, 1, 100);
    IntegerSetting maxDelay = registerInteger("Max Delay", 5, 1, 100);

    public Spammer() {
        updateTimes();
    }

    private long lastTime, delay;
    private Random random = new Random(System.currentTimeMillis());
    private static final Random RNG = new Random();

    public void onUpdate() {
        if (delay > Math.max(minDelay.getValue(), maxDelay.getValue()))
            delay = Math.max(minDelay.getValue(), maxDelay.getValue());
        else if (delay < Math.min(minDelay.getValue(), maxDelay.getValue()))
            delay = Math.min(minDelay.getValue(), maxDelay.getValue());
        if (System.currentTimeMillis() >= lastTime + 1000 * delay) {
        	
        	int messagesToxic = random(1, 26);
            int messagesAdvertise = random(1, 16);
            int messagesAntiRacist = random(1, 14);
            int messagesClients = random(1, 19);
            
            switch(mode.getValue()) {
            case "Toxic": {
            	switch(messagesToxic) {
            	case 1: {
            		MessageBus.sendServerMessage("> my richness powered by spidersense");
            		break;
            	}
            	case 2: {
            		MessageBus.sendServerMessage("> im doing your mom rn no cap");
            		break;
            	}
            	case 3: {
            		MessageBus.sendServerMessage("> stfu pooron");
            		break;
            	}
            	case 4: {
            		MessageBus.sendServerMessage("> LOL COPE MORE KID");
            		break;
            	}
            	case 5: {
            		MessageBus.sendServerMessage("> say one more word and your getting swatted faggot");
            		break;
            	}
            	case 6: {
            		MessageBus.sendServerMessage("> the iq of some people on this server is so low it's astonishing");
            		break;
            	}
            	case 7: {
            		MessageBus.sendServerMessage("> shut your little cumhole retard, im richer than you thanks to spidersense");
            		break;
            	}
            	case 8: {
            		MessageBus.sendServerMessage("> compare funds rn so poor lmfao");
            		break;
            	}
            	case 9: {
            		MessageBus.sendServerMessage("> keep using future little cunt fanboy");
            		break;
            	}
            	case 10: {
            		MessageBus.sendServerMessage("> LOL the shittalker is getting SWATTED in vc join now!");
            		break;
            	}
            	case 11: {
            		MessageBus.sendServerMessage("> go craw back into mummy's pussy little incel");
            		break;
            	}
            	case 12: {
            		MessageBus.sendServerMessage("> the dox squad is after you bro!");
            		break;
            	}
            	case 13: {
            		MessageBus.sendServerMessage("> your iq is below room tempature! that's cold bro");
            		break;
            	}
            	case 14: {
            		MessageBus.sendServerMessage("> imagine being bad, it's just something i can't relate to");
            		break;
            	}
            	case 15: {
            		MessageBus.sendServerMessage("> BRO YOUR WINDOW IS ABOUT TO GET SMASHED BY THE II GOON SQUAD!!!!");
            		break;
            	}
            	case 16: {
            		MessageBus.sendServerMessage("> ran impact? i backdoored it LOOLLLLOLLOOLOLOL");
            		break;
            	}
            	case 17: {
            		MessageBus.sendServerMessage("> my crystal will crawl right up your ass shitter");
            		break;
            	}
            	case 18: {
            		MessageBus.sendServerMessage("> XDDD the 11 year old is crying in vc cuz he got swatted LMFAOOOO");
            		break;
            	}
            	case 19: {
            		MessageBus.sendServerMessage("> the goon squad will roll up to your door if you don't shut the fuck up pooron");
            		break;
            	}
            	case 20: {
            		MessageBus.sendServerMessage("> I OWN U KID");
            		break;
            	}
            	case 21: {
            		MessageBus.sendServerMessage("> i will fuck your ass so hard in cpvp your ass will hurr till' your dead LOL");
            		break;
            	}
            	case 22: {
            		MessageBus.sendServerMessage("> bro he's getting doxxed, HES GETTING DOXXED!!!");
            		break;
            	}
            	case 23: {
            		MessageBus.sendServerMessage("> look behind you faggot");
            		break;
            	}
            	case 24: {
            		MessageBus.sendServerMessage("> IMAGINE LOSING TO TRANNIE HAHAHAHAAHAHAHA");
            		break;
            	}
            	case 25: {
            		MessageBus.sendServerMessage("> learn to cope and seethe jesus");
            		break;
            	}
            	case 26: {
            		MessageBus.sendServerMessage("my coolness powered by hausemasterissue and his epic client");
            		break;
            	}
            			
            		
            	}
            }
            
            case "Advertise": {
            	switch(messagesAdvertise) {
            	case 1: {
            		MessageBus.sendServerMessage("> spidersense isn't a rat dumbass");
            		break;
            	}
            	case 2: {
            		MessageBus.sendServerMessage("> stop using 0xbackdoor (aka future), use spidersense");
            		break;
            	}
            	case 3: {
            		MessageBus.sendServerMessage("> HOLY SHIT i can't stop winning thanks to spidersense");
            		break;
            	}
            	case 4: {
            		MessageBus.sendServerMessage("> bro imagine using gamesense, spidersense is on top");
            		break;
            	}
            	case 5: {
            		MessageBus.sendServerMessage("> BRO THE CA IS SO FAST IN SPIDERSENSE WTF");
            		break;
            	}
            	case 6: {
            		MessageBus.sendServerMessage("> the spidersense dev deobfuscated future lol");
            		break;
            	}
            	case 7: {
            		MessageBus.sendServerMessage("> hausemasterissue is on top baby! u mad newfag? u mad faggot?");
            		break;
            	}
            	case 8: {
            		MessageBus.sendServerMessage("> thanks to spidersense im staying WIDE baby WIDE");
            		break;
            	}
            	case 9: {
            		MessageBus.sendServerMessage("> spidersense owns me and all");
            		break;
            	}
            	case 10: {
            		MessageBus.sendServerMessage("> spidersense is free, open source, and SAFE! check the src yourself!");
            		break;
            	}
            	case 11: {
            		MessageBus.sendServerMessage("> download spidersense FOR FREE at github.com/hausemasterissue/spidersense");
            		break;
            	}
            	case 12: {
            		MessageBus.sendServerMessage("> the dev of spidersense added more updates in 2 weeks than future get's in a WHOLE YEAR LOOL");
            		break;
            	}
            	case 13: {
            		MessageBus.sendServerMessage("> spidersense is 100% not skidded");
            		break;
            	}
            	case 14: {
            		MessageBus.sendServerMessage("> spidersense totally never pasted any code from cousinware, cosmos, momentum, legacy, future, or oyvey!");
            		break;
            	}
            	case 16: {
            		MessageBus.sendServerMessage("> HAUSEMASTERISSUE IS BASED BEYOND ANYTHING TO EVER EXIST");
            		break;
            	}
            	}
            }
            
            case "AntiRacist": {
            	switch(messagesAntiRacist) {
            	case 1: {
            		MessageBus.sendServerMessage("> bro imagine being racist in 2021, cringe");
            		break;
            	}
            	case 2: {
            		MessageBus.sendServerMessage("> WE ARE ALL EQUAL");
            		break;
            	}
            	case 3: {
            		MessageBus.sendServerMessage("> Do not judge on color, judge on personality.");
            		break;
            	}
            	case 4: {
            		MessageBus.sendServerMessage("> #BLM");
            		break;
            	}
            	case 5: {
            		MessageBus.sendServerMessage("> Color TV was meant for white people, together we can make it for all skin colors!");
            		break;
            	}
            	case 6: {
            		MessageBus.sendServerMessage("> SUPPORT BLM");
            		break;
            	}
            	case 7: {
            		MessageBus.sendServerMessage("> Stop being racist, be kind");
            		break;
            	}
            	case 8: {
            		MessageBus.sendServerMessage("> END WHITE SUPREMACY");
            		break;
            	}
            	case 9: {
            		MessageBus.sendServerMessage("> Wao, PopBob, FitMC and SalC1 all remind you to support BLM!");
            		break;
            	}
            	case 10: {
            		MessageBus.sendServerMessage("> Want to end racism? Vote Obama 2024");
            		break;
            	}
            	case 11: {
            		MessageBus.sendServerMessage("> END RACSIM, JOIN THE PUNXATAWNEY GROUNDHOG CLUB");
            		break;
            	}
            	case 12: {
            		MessageBus.sendServerMessage("> Help end segregation in South Africa, watch KiLAB Gaming on YT");
            		break;
            	}
            	case 13: {
            		MessageBus.sendServerMessage("> Black or white, love is love.");
            		break;
            	}
            	case 14: {
            		MessageBus.sendServerMessage("> HausemasterIssue and SpiderSense client support BLM!");
            		break;
            	}
                }
            }
            
            case "Clients": {
            	switch(messagesClients) {
            	case 1: {
            		MessageBus.sendServerMessage("> Imagine using Wurst+2 in 2021, Cringe");
            		break;
            	}
            	case 2: {
            		MessageBus.sendServerMessage("> Don't buy Pyro, it's just a SalHack skid");
            		break;
            	}
            	case 3: {
            		MessageBus.sendServerMessage("> SalHack in 2021? Really?");
            		break;
            	}
            	case 4: {
            		MessageBus.sendServerMessage("> The developers of Future are cunts, don't give them your $20");
            		break;
            	}
            	case 5: {
            		MessageBus.sendServerMessage("> John is based. Go buy his insane client at rusherhack.org");
            		break;
            	}
            	case 6: {
            		MessageBus.sendServerMessage("> Inertia is bad, stop using it");
            		break;
            	}
            	case 7: {
            		MessageBus.sendServerMessage("> Why are you using phobos when you aren't playing on cc? Get real");
            		break;
            	}
            	case 8: {
            		MessageBus.sendServerMessage("> Travis is sexy, so is MrWorldWide, go use his chad client Wurst+3");
            		break;
            	}
            	case 9: {
            		MessageBus.sendServerMessage("> Please for the love of god, STOP USING KAMI BLUE, FUCK KAMI BLUE!");
            		break;
            	}
            	case 10: {
            		MessageBus.sendServerMessage("> Lambda is coded in Kotin, what a bunch of weirdos");
            		break;
            	}
            	case 11: {
            		MessageBus.sendServerMessage("> Inferno is so good, Sxmurai did an amazing job");
            		break;
            	}
            	case 12: {
            		MessageBus.sendServerMessage("> GameSense is discontinued, what a tragedy");
            		break;
            	}
            	case 13: {
            		MessageBus.sendServerMessage("> Konas is so dogshit, it's been cracked by the fellas at PlutoSolutions for free, don't pay $15 for it");
            	}
            	case 14: {
            		MessageBus.sendServerMessage("> #Gondal.clubNeverForget");
            		break;
            	}
            	case 15: {
            		MessageBus.sendServerMessage("> RenoSense is a RAT, it has been confirmed");
            		break;
            	}
            	case 16: {
            		MessageBus.sendServerMessage("> Abyss get's worse by every version, it's sad at this point");
            		break;
            	}
            	case 17: {
            		MessageBus.sendServerMessage("> Catalyst.sexy is not sexy at all");
            		break;
            	}
            	case 18: {
            		MessageBus.sendServerMessage("> Impact is a RAT, wipe your PC right away");
            		break;
            	}
            	case 19: {
            		MessageBus.sendServerMessage("> SpiderSense is the best client in existence");
            		break;
            	}
            	}
            }
            
            }
        }
    }

    private void updateTimes() {
        lastTime = System.currentTimeMillis();
        int bound = Math.abs(maxDelay.getValue() - minDelay.getValue());
        delay = (bound == 0 ? 0 : random.nextInt(bound)) + Math.min(maxDelay.getValue(), minDelay.getValue());
    }
    
    private int random(int min, int max) {
        return RNG.nextInt(max + min) - min;
    }
}
