from datetime import datetime

import requests
from bs4 import BeautifulSoup


def main():
    s = requests.Session()
    s.headers.update({
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/108.0.0.0 Safari/537.36'
    })
    findThoseGames = ['FAR%3A+Changing+Tides', "Wasteland+2%3A+Director%27s+Cut", 'Moonscars',
                      'DC+League+of+Super-Pets%3A+The+Adventures+of+Krypto+and+Ace',
                      'Danganronpa+V3%3A+Killing+Harmony+Anniversary+Edition', 'Loot+River', 'Watch+Dogs+2',
                      'Wasteland+Remastered', 'Ori+and+the+Will+of+the+Wisps', 'The+Riftbreaker', "No+Man%27s+Sky",
                      'Outer+Wilds', 'Kentucky+Route+Zero%3A+TV+Edition', 'Mortal+Kombat+11', 'The+Ascent',
                      'My+Friend+Pedro', 'Fallout+4', 'State+of+Decay+2%3A+Juggernaut+Edition', 'Battletoads',
                      'Chinatown+Detective+Agency', 'NBA+2K22+%28Xbox+One%29', 'Microsoft+Flight+Simulator',
                      'Undertale', 'DOOM', 'Next+Space+Rebels', 'Nongunz%3A+Doppelganger+Edition', 'Banjo-Tooie',
                      'Neoverse', 'SIGNALIS', 'Matchpoint%3A+Tennis+Championships', "Mirror%27s+Edge+Catalyst",
                      'Terraria', 'DayZ', 'ClusterTruck', 'The+Forgotten+City', 'Pillars+Of+Eternity+II%3A+Deadfire',
                      'INSIDE', 'Gears+of+War%3A+Ultimate+Edition', '7+Days+to+Die',
                      "The+Bard%27s+Tale+IV%3A+Director%27s+Cut", "Tom+Clancy%27s+Ghost+Recon+Wildlands", 'Dreamscaper',
                      'Gears+of+War+2', 'TUNIC', 'Recompile', 'Dead+by+Daylight', 'Wo+Long%3A+Fallen+Dynasty',
                      'Secret+Neighbor', 'Serious+Sam+4', 'Halo%3A+Spartan+Assault', 'Telling+Lies', 'Moonglow+Bay',
                      'Neon+Abyss', 'The+Pedestrian', 'Moonlighter',
                      'Ni+no+Kuni%3A+Wrath+of+the+White+Witch+Remastered', 'Just+Cause+4', 'Trek+to+Yomi',
                      'Gears+of+War+3', 'Crackdown+3', "Teenage+Mutant+Ninja+Turtles%3A+Shredder%27s+Revenge",
                      'Coffee+Talk', 'Metal%3A+Hellsinger+%28Xbox+One%29',
                      'The+Walking+Dead%3A+Michonne', 'Signs+of+the+Sojourner', 'Resident+Evil+7%3A+Biohazard',
                      'Totally+Reliable+Delivery+Service', 'The+Evil+Within+2', 'Conan+Exiles', 'DIRT+5',
                      "Assassin%27s+Creed+Origins", 'Wreckfest', 'Pac-Man+Museum%2B', 'Injustice+2',
                      'Battlefield+2042+%28Xbox+One%29', 'Dragon+Age+II', 'Dead+Space+%282008%29', 'MotoGP+22',
                      'Skul%3A+The+Hero+Slayer', 'As+Dusk+Falls', 'Persona+3+Portable', 'Townscaper', 'Contrast',
                      'My+Friend+Peppa+Pig', 'Goat+Simulator', 'Yakuza+6%3A+The+Song+of+Life',
                      'Dragon+Age%3A+Inquisition',
                      'The+Good+Life', 'Warhammer+40%2C000%3A+Battlesector', 'Forza+Horizon+4', 'Supraland', 'Chorus',
                      'Mind+Scanners', 'Shadowrun+Returns', 'SHE+DREAMS+ELSEWHERE', 'Back+4+Blood', 'ReCore',
                      'Deep+Rock+Galactic', 'For+Honor', 'Disneyland+Adventures', 'Dragon+Ball+FighterZ',
                      "New+Super+Lucky%27s+Tale", 'Persona+4+Golden', 'The+Walking+Dead%3A+Season+Two', 'Young+Souls',
                      'Farming+Simulator+22', 'Twelve+Minutes', 'Overcooked%21+2', 'RAGE', 'HITMAN', 'Forza+Motorsport',
                      'SpiderHeck', 'Destroy+All+Humans%21', 'Fae+Tactics', 'Kill+It+With+Fire', 'Maneater',
                      'Solasta%3A+Crown+of+the+Magister', 'Disney+Dreamlight+Valley',
                      "Shadowrun%3A+Dragonfall+-+Director%27s+Cut", 'A+Memoir+Blue', 'Wolfenstein%3A+The+Old+Blood',
                      'Battlefield+1', 'Gears+5', 'Yakuza+Kiwami', 'Shredders', 'Skate+3', 'Stardew+Valley',
                      "Senua%27s+Saga%3A+Hellblade+II", 'Kraken+Academy%21%21', 'Pikuniku',
                      "Hellblade%3A+Senua%27s+Sacrifice",
                      'Immortality', 'Life+is+Strange%3A+True+Colors', 'Tinykin', 'Citizen+Sleeper',
                      'Bridge+Constructor+Portal', 'Fallout+76', 'Naraka%3A+Bladepoint', 'Besiege',
                      'Star+Wars+Jedi%3A+Fallen+Order', 'Beacon+Pines', 'Mass+Effect%3A+Andromeda', 'Peggle+2',
                      'Broken+Age', 'Dead+Cells', 'Gears+of+War%3A+Judgment', 'Dragon+Age%3A+Origins', 'Gears+Tactics',
                      'Dishonored+Definitive+Edition', 'Lawn+Mowing+Simulator', 'Kameo%3A+Elements+of+Power',
                      'Dishonored%3A+Death+of+the+Outsider', 'Aragami+2', "Tom Clancy's Rainbow Six Siege",
                      'Cities%3A+Skylines', 'Nobody+Saves+the+World', 'Superliminal', 'Before+We+Leave',
                      'Evil+Genius+2%3A+World+Domination', "Death's Door",
                      'Ori+and+the+Blind+Forest%3A+Definitive+Edition',
                      'Jurassic+World+Evolution+2', 'Halo+Wars%3A+Definitive+Edition', 'HITMAN+2', 'Sniper+Elite+5',
                      'Ninja+Gaiden+Σ', 'Killer+Instinct', 'Ben+10%3A+Power+Trip', 'It+Takes+Two', 'Double+Dragon+Neon',
                      'PAW+Patrol%3A+Mighty+Pups+Save+Adventure+Bay', 'Yakuza+5+Remastered', 'Yakuza%3A+Like+a+Dragon',
                      'Train+Sim+World+3', 'Star+Wars+Battlefront+II', 'Wolfenstein%3A+The+New+Order',
                      'Viva+Piñata%3A+Trouble+In+Paradise', 'Record+of+Lodoss+War%3A+Deedlit+in+Wonder+Labyrinth',
                      'A+Plague+Tale%3A+Requiem', 'Super+Mega+Baseball+3', 'Batman%3A+Arkham+Knight',
                      'ANVIL%3A+Vault+Breaker', 'Golf+With+Your+Friends', 'Middle-earth%3A+Shadow+of+War',
                      'Wolfenstein%3A+Youngblood', 'The+Last+Kids+on+Earth+and+the+Staff+of+Doom', 'Chivalry+2',
                      'My+Time+at+Portia', 'Phoenix+Point', 'Banjo-Kazooie',
                      'Opus%3A+Echo+of+Starsong+–+Full+Bloom+Edition', 'Valheim',
                      'Space+Warlord+Organ+Trading+Simulator', 'Pillars+of+Eternity',
                      "Tom+Clancy%27s+Rainbow+Six+Extraction", 'Descenders',
                      'LEGO+Star+Wars%3A+The+Skywalker+Saga', 'Pupperazzi', 'Totally+Accurate Battle+Simulator',
                      'Unravel+Two', 'CrossfireX', 'Plants+vs.+Zombies+Garden+Warfare', 'Transformers%3A+Battlegrounds',
                      'Joy+Ride+Turbo', 'ARK%3A+Survival+Evolved', 'DOOM+64', 'The+Outer+Worlds',
                      'Halo%3A+The+Master+Chief+Collection', 'Midnight+Fight+Express', 'Outriders',
                      'Dragon+Quest+Builders+2', 'Tainted+Grail%3A+Conquest',
                      'PAW+Patrol+The+Movie%3A+Adventure+City+Calls',
                      'The+Evil+Within', 'Little+Witch+in+the+Woods', 'One+Step+From+Eden',
                      'What+Remains+of+Edith+Finch', 'Grounded', 'Sea+of+Thieves',
                      'Jetpac+Refuelled', 'Windjammers+2', 'Tell+Me+Why', 'RAGE+2', 'The+Sims+4', 'Gears+of+War+4',
                      'Weird+West', 'Floppy+Knights', 'Perfect+Dark+Zero', 'Astroneer', 'The+Last+Case+of+Benedict+Fox',
                      'Gungrave G.O.R.E', 'Fortnite', "Marvel's Avengers", 'Race+with+Ryan', 'skate.', 'HITMAN+3',
                      'Halo+Wars+2', 'Mortal+Shell%3A+Enhanced+Edition', 'OMORI', 'Empire+of+Sin', 'Ghost+Song',
                      'Lost+In+Random', 'Insurgency%3A+Sandstorm', 'Fable+II', 'Among+Us',
                      'Rush%3A+A+Disney+Pixar+Adventure', 'Forza+Motorsport+7', 'PAW+Patrol%3A+Grand+Prix', 'Myst',
                      'DOOM+II+%28Classic%29', 'Amnesia%3A+Rebirth', 'Fallout+3', 'Garden+Story', 'Worms+W.M.D.',
                      'Need+for+Speed+Hot+Pursuit+Remastered', 'NBA+2K22', 'Spiritfarer%3A+Farewell+Edition',
                      'Ring+of+Pain', 'Crusader+Kings+III', 'Olija', 'SnowRunner',
                      'Generation+Zero', 'Eastward', 'Need+for+Speed+Heat', 'Yakuza+3+Remastered', 'Ninja+Gaiden+Σ2',
                      'Black+Desert', 'Surgeon+Simulator+2', 'Danganronpa+2%3A+Goodbye+Despair+Anniversary+Edition',
                      'Banjo-Kazooie%3A+Nuts+%26+Bolts', 'Gunfire+Reborn', 'Sable', 'Way+to+the+Woods',
                      'Torment%3A+Tides+of+Numenera', 'Cricket+22', 'Eiyuden+Chronicle%3A+Rising', 'Tropico+6',
                      'Gang+Beasts', 'Commandos+3+-+HD+Remaster', 'Wasteland+3', 'Forza+Horizon+5', 'The+Long+Dark',
                      'Pentiment', 'The+Dungeon+of+Naheulbeuk%3A+The+Amulet+of+Chaos+-+Chicken+Edition',
                      'Bugsnax', 'Unsouled', 'High+On+Life', 'DOOM+Eternal', 'Psychonauts+2', 'VAPOR+World',
                      'Paradise+Killer', 'Edge+of+Eternity', 'Slay+The+Spire', "Despot%27s+Game",
                      'Wolfenstein+II%3A+The+New+Colossus', 'Rubber+Bandits', 'Perfect+Dark',
                      'Plants+vs.+Zombies+Garden+Warfare+2', 'Halo+5%3A+Guardians', 'Eville']
    game(s, findThoseGames)


""" findThoseGames = ['Battlefield+4', 'Frostpunk%3A+Console+Edition', 'The+Gunk', "The+Bard%27s+Tale+Trilogy",
                      'Disc+Room', 'Crown+Trick', 'Amnesia%3A+Collection', 'F1+2021', 'The+Walking+Dead',
                      "The+Bard%27s+Tale+ARPG+%3A+Remastered+and+Resnarkled", 'Viva+Piñata', 'Bleeding+Edge',
                      'Nuclear+Throne', 'theHunter%3A+Call+of+the+Wild', 'Infernax', 'Octopath+Traveler',
                      'Turnip+Boy+Commits+Tax+Evasion', 'Yakuza+Kiwami+2', 'Quake',
                      'Umurangi+Generation+Special+Edition', 'Metal%3A+Hellsinger', 'Spelunky+2',
                      'Lonely+Mountains%3A+Downhill', 'The+Walking+Dead%3A+A+New+Frontier', 'Two+Point+Campus',
                      'Yakuza+0', 'Battlefield+V', 'Hollow+Knight%3A+Voidheart+Edition', 'Spacelines+from+the+Far+Out',
                      'Far+Cry+5', 'Chained+Echoes', 'Dead+Static+Drive', "Let%27s+Build+a+Zoo", 'Dicey+Dungeons',
                      'House+Flipper', 'Persona+5+Royal', 'ONE+PIECE%3A+PIRATE+WARRIORS+4', 'Genesis+Noir', 'Road+96',
                      'Star+Wars%3A+Squadrons', 'Stellaris%3A+Console+Edition', 'This+War+of+Mine%3A+Final+Cut', 'Prey',
                      'You+Suck+at+Parking', 'Dishonored+2', 'Yakuza+4+Remastered', 'Costume+Quest', 'World+War+Z',
                      'Forager', 'We+Happy+Few', 'Cooking+Simulator', 'Medieval+Dynasty', 'Scarlet+Nexus',
                      'Human+Fall+Flat', 'RESEARCH+and+DESTROY', 'Death+loop', 'Unpacking', 'Two+Point+Hospital',
                      'MechWarrior+5%3A+Mercenaries', 'Minecraft+Dungeons',
                      "Assassin%27s+Creed+Odyssey", 'DOOM+%281993%29', 'Hardspace%3A+Shipbreaker',
                      'Shadowrun%3A+Hong+Kong+-+Extended+Edition', 'Fable+Anniversary',
                      "Marvel%27s+Guardians+of+the+Galaxy",
                      'MLB+The+Show+22', 'Subnautica', 'Fallout%3A+New+Vegas', 'Immortals+Fenyx+Rising', 'Fable+III',
                      'Battlefield+2042', 'PowerWash+Simulator', 'The+Elder+Scrolls+V%3A+Skyrim+Special+Edition',
                      'Second+Extinction', 'DOOM+3', 'Exo+One', 'Prodeus', 'The Elder Scrolls+IV+%3A+Oblivion',
                      'Sniper+Elite+4', 'Project+Wingman', 'Mass+Effect+Legendary+Edition',
                      'Plants+vs.+Zombies%3A+Battle+for+Neighborville', 'Trailmakers', 'Fuga%3A+Melodies+of+Steel',
                      'Hello+Neighbor+2', 'Donut+County', 'Infinite+Guitars', 'Power+Rangers%3A+Battle+for+the+Grid',
                      'Halo+Infinite', 'DJMAX+Respect+V', "Ninja+Gaiden+3%3A+Razor%27s+Edge",
                      'Football+Manager+2023+Console']
"""


def game(session, findThoseGames):
    print(f"Total Games: {len(findThoseGames)}")
    for gameParse in findThoseGames:
        url = f"https://www.xbox.com/es-ES/play/search?q={gameParse}"
        print(f"Link: {url}")
        resp = session.get(url)
        soup = BeautifulSoup(resp.text, "html.parser")
        hardware = soup.findAll('h1')
        if "No se" in hardware[1].text:
            print(hardware[1].text)


if __name__ == '__main__':
    main()
