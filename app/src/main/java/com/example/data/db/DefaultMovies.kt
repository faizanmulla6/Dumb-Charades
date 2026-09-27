package com.example.data.db

import com.example.data.model.MovieEntity

object DefaultMovies {
    val list: List<MovieEntity> = listOf(
        // HOLLYWOOD HITS
        MovieEntity(
            title = "Titanic",
            category = "Hollywood Hits",
            hint = "Giant ship hitting an iceberg, Jack & Rose, 'I'm the king of the world!'",
            secondaryHint = "Act like you are standing on the bow with open arms, then pretend to freeze in water.",
            difficulty = "Easy",
            year = 1997,
            wordCount = 1
        ),
        MovieEntity(
            title = "Inception",
            category = "Hollywood Hits",
            hint = "Dreams inside dreams, spinning metal top, city streets folding upwards.",
            secondaryHint = "Act like you are spinning a top, falling backwards, or sleeping deeply.",
            difficulty = "Medium",
            year = 2010,
            wordCount = 1
        ),
        MovieEntity(
            title = "The Dark Knight",
            category = "Hollywood Hits",
            hint = "Batman battling the Joker, Gotham City, 'Why so serious?'",
            secondaryHint = "Put fingers on face like Batman ears, then do a manic creepy Joker smile.",
            difficulty = "Easy",
            year = 2008,
            wordCount = 3
        ),
        MovieEntity(
            title = "Jurassic Park",
            category = "Hollywood Hits",
            hint = "Island theme park with cloned dinosaurs, water glass vibrating to T-Rex steps.",
            secondaryHint = "Act like a menacing T-Rex with short arms and huge roaring jaws.",
            difficulty = "Easy",
            year = 1993,
            wordCount = 2
        ),
        MovieEntity(
            title = "The Matrix",
            category = "Hollywood Hits",
            hint = "Red pill or blue pill, dodging bullets in slow motion, Neo & Morpheus.",
            secondaryHint = "Do the iconic slow-motion backward lean dodging bullets.",
            difficulty = "Medium",
            year = 1999,
            wordCount = 2
        ),
        MovieEntity(
            title = "Avatar",
            category = "Hollywood Hits",
            hint = "Tall blue alien beings on Pandora, flying banshees, Tree of Souls.",
            secondaryHint = "Pretend to have a long tail, shooting arrows, and connecting with a spirit tree.",
            difficulty = "Easy",
            year = 2009,
            wordCount = 1
        ),
        MovieEntity(
            title = "The Avengers",
            category = "Hollywood Hits",
            hint = "Earth's mightiest heroes: Iron Man, Cap, Thor, Hulk, battling Loki in NYC.",
            secondaryHint = "Act like Iron Man blasting palms, then smash fists like Hulk.",
            difficulty = "Easy",
            year = 2012,
            wordCount = 2
        ),
        MovieEntity(
            title = "Pirates of the Caribbean",
            category = "Hollywood Hits",
            hint = "Captain Jack Sparrow, Black Pearl pirate ship, compass that doesn't point north.",
            secondaryHint = "Stumble drunk like Jack Sparrow with swaggering pirate hands.",
            difficulty = "Medium",
            year = 2003,
            wordCount = 4
        ),
        MovieEntity(
            title = "Interstellar",
            category = "Hollywood Hits",
            hint = "Wormhole near Saturn, traveling through black hole, bookshelf communication.",
            secondaryHint = "Pretend to float in zero gravity, tap invisible books on a shelf.",
            difficulty = "Hard",
            year = 2014,
            wordCount = 1
        ),
        MovieEntity(
            title = "Home Alone",
            category = "Hollywood Hits",
            hint = "Young boy Kevin left alone at Christmas defends home from two burglars.",
            secondaryHint = "Scream with both hands slapped onto your cheeks!",
            difficulty = "Easy",
            year = 1990,
            wordCount = 2
        ),
        MovieEntity(
            title = "The Godfather",
            category = "Hollywood Hits",
            hint = "Mafia family Corleone, horse head in bed, 'Make him an offer he cannot refuse.'",
            secondaryHint = "Stroke an imaginary cat in your lap and speak with muffled cheeks.",
            difficulty = "Medium",
            year = 1972,
            wordCount = 2
        ),
        MovieEntity(
            title = "Gladiator",
            category = "Hollywood Hits",
            hint = "Roman general Maximus forced to fight as gladiator in the Colosseum.",
            secondaryHint = "Swing a gladiator sword and shield, give a Roman emperor thumbs down.",
            difficulty = "Medium",
            year = 2000,
            wordCount = 1
        ),
        MovieEntity(
            title = "Back to the Future",
            category = "Hollywood Hits",
            hint = "DeLorean time machine, Doc Brown with wild hair, 88 miles per hour, hoverboard.",
            secondaryHint = "Check wristwatch frantically, act out an electric lightning shock!",
            difficulty = "Medium",
            year = 1985,
            wordCount = 4
        ),
        MovieEntity(
            title = "Forrest Gump",
            category = "Hollywood Hits",
            hint = "Box of chocolates, running across America, ping pong champion, bench story.",
            secondaryHint = "Wave naively like Forrest, then start running continuously in place.",
            difficulty = "Easy",
            year = 1994,
            wordCount = 2
        ),
        MovieEntity(
            title = "Pulp Fiction",
            category = "Hollywood Hits",
            hint = "Two hitmen talking about burgers, twist dance contest, glowing golden briefcase.",
            secondaryHint = "Do the two-fingers across the eyes twist dance.",
            difficulty = "Hard",
            year = 1994,
            wordCount = 2
        ),

        // BOLLYWOOD BLOCKBUSTERS
        MovieEntity(
            title = "Sholay",
            category = "Bollywood Blockbusters",
            hint = "Jai and Veeru, bandit Gabbar Singh, 'Kitne aadmi the?', Basanti dance on glass.",
            secondaryHint = "Pretend to have no arms like Thakur, or do Basanti's dramatic dance.",
            difficulty = "Easy",
            year = 1975,
            wordCount = 1
        ),
        MovieEntity(
            title = "Dilwale Dulhania Le Jayenge",
            category = "Bollywood Blockbusters",
            hint = "Raj & Simran, Eurail train journey, mustard fields in Punjab, 'Bade bade deshon mein...'",
            secondaryHint = "Reach hand out desperately from a moving train door!",
            difficulty = "Easy",
            year = 1995,
            wordCount = 4
        ),
        MovieEntity(
            title = "3 Idiots",
            category = "Bollywood Blockbusters",
            hint = "Imperial College of Engineering, Rancho, Virus, 'All Is Well', missing friend search.",
            secondaryHint = "Pat your heart in circles chanting 'Aal Izz Well'!",
            difficulty = "Easy",
            year = 2009,
            wordCount = 2
        ),
        MovieEntity(
            title = "Lagaan",
            category = "Bollywood Blockbusters",
            hint = "Villagers challenge British officers to a cricket match to cancel land tax.",
            secondaryHint = "Pretend to spin bowl cricket ball with a dhoti gesture.",
            difficulty = "Medium",
            year = 2001,
            wordCount = 1
        ),
        MovieEntity(
            title = "Dangal",
            category = "Bollywood Blockbusters",
            hint = "Mahavir Phogat trains daughters Geeta & Babita in wrestling for gold medals.",
            secondaryHint = "Slap your thighs and grapple like an Indian wrestler in mud.",
            difficulty = "Easy",
            year = 2016,
            wordCount = 1
        ),
        MovieEntity(
            title = "Baahubali",
            category = "Bollywood Blockbusters",
            hint = "Kingdom of Mahishmati, Shivudu lifting massive Shiva lingam under waterfall.",
            secondaryHint = "Pretend to lift a giant stone idol onto your shoulder with strength.",
            difficulty = "Easy",
            year = 2015,
            wordCount = 1
        ),
        MovieEntity(
            title = "Kabhi Khushi Kabhie Gham",
            category = "Bollywood Blockbusters",
            hint = "Helicopter landing, mother sensing son's arrival with pooja thali, big family drama.",
            secondaryHint = "Pretend to hold a pooja thali turning around with sudden motherly intuition.",
            difficulty = "Medium",
            year = 2001,
            wordCount = 4
        ),
        MovieEntity(
            title = "Zindagi Na Milegi Dobara",
            category = "Bollywood Blockbusters",
            hint = "Three friends road trip in Spain, skydiving, deep sea diving, Tomatina tomato festival.",
            secondaryHint = "Throw imaginary tomatoes at everyone or act out falling from plane.",
            difficulty = "Medium",
            year = 2011,
            wordCount = 5
        ),
        MovieEntity(
            title = "Hera Pheri",
            category = "Bollywood Blockbusters",
            hint = "Baburao Apte, Raju, Shyam, wrong number cross connection ransom call from Kabira.",
            secondaryHint = "Pretend to adjust spectacles like Baburao and scream on a telephone.",
            difficulty = "Easy",
            year = 2000,
            wordCount = 2
        ),
        MovieEntity(
            title = "Chak De! India",
            category = "Bollywood Blockbusters",
            hint = "Kabir Khan coaches underdog women's national field hockey team to World Cup win.",
            secondaryHint = "Swing a field hockey stick and show 70 minutes with 7 fingers!",
            difficulty = "Medium",
            year = 2007,
            wordCount = 3
        ),
        MovieEntity(
            title = "RRR",
            category = "Bollywood Blockbusters",
            hint = "Revolutionary fighters Alluri Sitarama Raju & Komaram Bheem, Naatu Naatu dance duel.",
            secondaryHint = "Do the high-energy synchronized suspenders Naatu Naatu leg hook step!",
            difficulty = "Easy",
            year = 2022,
            wordCount = 1
        ),
        MovieEntity(
            title = "Munna Bhai M.B.B.S.",
            category = "Bollywood Blockbusters",
            hint = "Local gangster enters medical college with cheating, solves problems with hugs (Jaadu ki jhappi).",
            secondaryHint = "Open arms wide for a warm comforting 'Jaadu ki Jhappi' hug!",
            difficulty = "Easy",
            year = 2003,
            wordCount = 3
        ),

        // ANIMATED & FAMILY
        MovieEntity(
            title = "The Lion King",
            category = "Animated & Family",
            hint = "Simba held atop Pride Rock, evil uncle Scar, Mufasa stampede, Hakuna Matata.",
            secondaryHint = "Hold an imaginary baby cub up in the air toward the sky.",
            difficulty = "Easy",
            year = 1994,
            wordCount = 3
        ),
        MovieEntity(
            title = "Finding Nemo",
            category = "Animated & Family",
            hint = "Clownfish father swims across ocean to find lost son with lucky fin, Dory speaks whale.",
            secondaryHint = "Flap hands like small fish fins and speak gibberish whale noises.",
            difficulty = "Easy",
            year = 2003,
            wordCount = 2
        ),
        MovieEntity(
            title = "Toy Story",
            category = "Animated & Family",
            hint = "Woody the cowboy doll and Buzz Lightyear space ranger, 'To infinity and beyond!'",
            secondaryHint = "Pull imaginary string on your back and salute with laser arm.",
            difficulty = "Easy",
            year = 1995,
            wordCount = 2
        ),
        MovieEntity(
            title = "Frozen",
            category = "Animated & Family",
            hint = "Elsa freezing everything into ice, snowman Olaf, sister Anna, 'Let It Go!'",
            secondaryHint = "Shoot imaginary frost and ice crystals from your fingertips!",
            difficulty = "Easy",
            year = 2013,
            wordCount = 1
        ),
        MovieEntity(
            title = "Shrek",
            category = "Animated & Family",
            hint = "Green ogre in swamp, talking donkey, rescue Princess Fiona from dragon castle.",
            secondaryHint = "Make funnel ogre ears with hands and stomp around angrily.",
            difficulty = "Easy",
            year = 2001,
            wordCount = 1
        ),
        MovieEntity(
            title = "Kung Fu Panda",
            category = "Animated & Family",
            hint = "Po the clumsy panda chosen as Dragon Warrior, noodle soup, dumpling training with chopsticks.",
            secondaryHint = "Do clumsy martial arts kicks while holding belly and snatching dumplings.",
            difficulty = "Easy",
            year = 2008,
            wordCount = 3
        ),
        MovieEntity(
            title = "Up",
            category = "Animated & Family",
            hint = "House lifted into sky by thousands of helium balloons, grumpy old Carl, Russell the scout.",
            secondaryHint = "Pretend to hold a massive bunch of balloons pulling you off the ground.",
            difficulty = "Medium",
            year = 2009,
            wordCount = 1
        ),
        MovieEntity(
            title = "Coco",
            category = "Animated & Family",
            hint = "Land of the Dead, guitar player Miguel, skeletal ancestors, 'Remember Me'.",
            secondaryHint = "Strum an imaginary Mexican guitar and point to a skeletal smile.",
            difficulty = "Medium",
            year = 2017,
            wordCount = 1
        ),
        MovieEntity(
            title = "Ratatouille",
            category = "Animated & Family",
            hint = "Rat Remy who loves gourmet cooking controls human chef by pulling his hair under chef hat.",
            secondaryHint = "Pretend someone is pulling the hair on top of your head to make you chop onions.",
            difficulty = "Medium",
            year = 2007,
            wordCount = 1
        ),
        MovieEntity(
            title = "Despicable Me",
            category = "Animated & Family",
            hint = "Gru with pointy nose, yellow Minions, freeze ray, stealing the Moon.",
            secondaryHint = "Point nose and freeze people with an imaginary freeze ray gun.",
            difficulty = "Easy",
            year = 2010,
            wordCount = 2
        ),

        // SCI-FI & ACTION
        MovieEntity(
            title = "The Terminator",
            category = "Sci-Fi & Action",
            hint = "Cyborg assassin sent back in time, leather jacket, 'I'll be back', thumbs up in lava.",
            secondaryHint = "Walk robotically, point shotgun, give slow thumbs up while sinking.",
            difficulty = "Easy",
            year = 1984,
            wordCount = 2
        ),
        MovieEntity(
            title = "Dune",
            category = "Sci-Fi & Action",
            hint = "Desert planet Arrakis, giant sand worms, blue spice eyes, Paul Atreides.",
            secondaryHint = "Do sand-walking rhythmic dance to avoid giant desert worms.",
            difficulty = "Hard",
            year = 2021,
            wordCount = 1
        ),
        MovieEntity(
            title = "Alien",
            category = "Sci-Fi & Action",
            hint = "Nostromo spaceship, facehugger egg, creature bursts from crew member's chest.",
            secondaryHint = "Clutch your chest in agony and push fist outward through your shirt!",
            difficulty = "Medium",
            year = 1979,
            wordCount = 1
        ),
        MovieEntity(
            title = "Men in Black",
            category = "Sci-Fi & Action",
            hint = "Secret agents in black suits, neuralyzer pen flashing light to erase alien memories.",
            secondaryHint = "Put on sunglasses, hold up a pen, and click it with a bright flash!",
            difficulty = "Easy",
            year = 1997,
            wordCount = 3
        ),
        MovieEntity(
            title = "E.T. the Extra-Terrestrial",
            category = "Sci-Fi & Action",
            hint = "Gentle alien with glowing finger, bicycle flying across moon, 'Phone home'.",
            secondaryHint = "Touch index fingers together and point to the sky saying 'Phone home'.",
            difficulty = "Easy",
            year = 1982,
            wordCount = 3
        ),
        MovieEntity(
            title = "Transformers",
            category = "Sci-Fi & Action",
            hint = "Optimus Prime, Bumblebee yellow Camaro, cars transforming into giant robot warriors.",
            secondaryHint = "Crouch into car shape then stand up stiffly making robotic whirring gestures.",
            difficulty = "Easy",
            year = 2007,
            wordCount = 1
        ),
        MovieEntity(
            title = "Mad Max: Fury Road",
            category = "Sci-Fi & Action",
            hint = "Post-apocalyptic wasteland, nitro war rigs, Immortan Joe, flaming electric guitar player.",
            secondaryHint = "Drive steering wheel like crazy and strum a flaming heavy metal guitar.",
            difficulty = "Hard",
            year = 2015,
            wordCount = 4
        ),
        MovieEntity(
            title = "John Wick",
            category = "Sci-Fi & Action",
            hint = "Legendary hitman in black suit avenging his stolen car and killed puppy dog.",
            secondaryHint = "Pet an invisible puppy, then whip out two pistols with pinpoint accuracy.",
            difficulty = "Medium",
            year = 2014,
            wordCount = 2
        ),
        MovieEntity(
            title = "Spider-Man",
            category = "Sci-Fi & Action",
            hint = "Peter Parker bitten by radioactive spider, climbing walls, shooting web from wrists.",
            secondaryHint = "Press two middle fingers to palm to shoot spider webs and swing!",
            difficulty = "Easy",
            year = 2002,
            wordCount = 1
        ),
        MovieEntity(
            title = "Star Wars",
            category = "Sci-Fi & Action",
            hint = "Darth Vader heavy breathing, Luke Skywalker, lightsaber duel, 'I am your father.'",
            secondaryHint = "Hold two hands on throat doing Darth Vader breathing and lightsaber swings.",
            difficulty = "Easy",
            year = 1977,
            wordCount = 2
        ),

        // COMEDY & CLASSICS
        MovieEntity(
            title = "The Mask",
            category = "Comedy & Classics",
            hint = "Green-faced mischievous hero with yellow zoot suit, spinning tornado, 'Smokin!'",
            secondaryHint = "Spin around in a wild circle and pull eyes out with exaggerated hands.",
            difficulty = "Easy",
            year = 1994,
            wordCount = 2
        ),
        MovieEntity(
            title = "Mr. Bean's Holiday",
            category = "Comedy & Classics",
            hint = "Odd silent Englishman in tweed jacket with red tie win a holiday trip to French beach.",
            secondaryHint = "Do the clumsy twitchy walk, strange goofy grin, and adjust red necktie.",
            difficulty = "Easy",
            year = 2007,
            wordCount = 3
        ),
        MovieEntity(
            title = "Dumb and Dumber",
            category = "Comedy & Classics",
            hint = "Lloyd and Harry in sheepdog van traveling to Aspen, orange and blue tuxedo suits.",
            secondaryHint = "Make foolish goofy face and give enthusiastic double thumbs up.",
            difficulty = "Easy",
            year = 1994,
            wordCount = 3
        ),
        MovieEntity(
            title = "Groundhog Day",
            category = "Comedy & Classics",
            hint = "Weather reporter stuck reliving the exact same day over and over at 6:00 AM.",
            secondaryHint = "Wake up in bed repeatedly, stare at alarm clock in frustration.",
            difficulty = "Medium",
            year = 1993,
            wordCount = 2
        ),
        MovieEntity(
            title = "Ghostbusters",
            category = "Comedy & Classics",
            hint = "Proton packs, trapping ghosts in NYC, Slimer, giant Stay Puft Marshmallow Man.",
            secondaryHint = "Carry heavy backpack, shoot an imaginary particle beam into a floor trap.",
            difficulty = "Medium",
            year = 1984,
            wordCount = 1
        ),
        MovieEntity(
            title = "The Hangover",
            category = "Comedy & Classics",
            hint = "Bachelor party in Las Vegas wakes up with missing groom, tiger in bathroom, lost tooth.",
            secondaryHint = "Check front tooth gap in mirror, look around confused with headache.",
            difficulty = "Medium",
            year = 2009,
            wordCount = 2
        ),
        MovieEntity(
            title = "Cast Away",
            category = "Comedy & Classics",
            hint = "FedEx worker stranded on deserted island, Wilson the volleyball with bloody handprint.",
            secondaryHint = "Scream 'WILSON!' while hugging a volleyball in despair.",
            difficulty = "Medium",
            year = 2000,
            wordCount = 2
        ),
        MovieEntity(
            title = "Jaws",
            category = "Comedy & Classics",
            hint = "Giant great white shark terrorizing beach town, 'You're gonna need a bigger boat.'",
            secondaryHint = "Put hand on head like shark fin, humming dun-dun... dun-dun... dun-dun!",
            difficulty = "Easy",
            year = 1975,
            wordCount = 1
        ),
        MovieEntity(
            title = "The Truman Show",
            category = "Comedy & Classics",
            hint = "Man discovers his entire suburban life is a secret 24-hour reality TV show.",
            secondaryHint = "Touch an invisible fake painted sky wall and bow to exit door.",
            difficulty = "Hard",
            year = 1998,
            wordCount = 3
        ),
        MovieEntity(
            title = "Night at the Museum",
            category = "Comedy & Classics",
            hint = "Night security guard discovers museum exhibits, dinosaur skeletons & wax figures come alive.",
            secondaryHint = "Flash flashlight, run away terrified from an animated T-Rex skeleton.",
            difficulty = "Medium",
            year = 2006,
            wordCount = 4
        )
    )
}
