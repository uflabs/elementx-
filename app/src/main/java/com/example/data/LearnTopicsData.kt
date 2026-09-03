package com.example.data

import com.example.model.LearnTopic

val learnTopics: List<LearnTopic> = listOf(
    LearnTopic(
        id = "what_is_an_element",
        title = "What is an Element?",
        iconName = "Science",
        summary = "A pure substance consisting entirely of one type of atom that cannot be broken down chemically.",
        content = "An element is a pure chemical substance made up of only one kind of atom. Every atom of an element has the exact same number of protons in its atomic nucleus.\n\nAll matter in the known universe—from the air you breathe to distant galaxies—is built from combinations of these 118 fundamental building blocks. Elements can combine chemically with other elements to form compounds, such as two Hydrogen atoms bonding with one Oxygen atom to produce water (H₂O).",
        keyTakeaways = listOf(
            "An element cannot be decomposed into simpler substances by ordinary chemical reactions.",
            "There are 118 officially recognized elements in the periodic table.",
            "94 occur naturally on Earth; the remaining 24 are created synthetically in particle accelerators."
        ),
        studentTip = "Think of elements like the primary colors of matter. Just as red, blue, and yellow mix to create every color, elements bond to create every substance on Earth!"
    ),
    LearnTopic(
        id = "what_is_atomic_number",
        title = "What is Atomic Number (Z)?",
        iconName = "Tag",
        summary = "The defining identity card of an atom: the exact count of protons in its nucleus.",
        content = "The atomic number (represented by the letter Z) is the number of positively charged protons located in the nucleus of an atom.\n\nThe atomic number is what defines the element. If an atom has 1 proton, it is always Hydrogen. If it gains or has 6 protons, it is always Carbon. If you change the number of protons, you transform the atom into an entirely different element!\n\nIn an electrically neutral atom, the atomic number also equals the total number of negatively charged electrons orbiting the nucleus.",
        keyTakeaways = listOf(
            "Atomic number = Number of Protons in the nucleus.",
            "The periodic table is arranged in order of increasing atomic number (1 to 118).",
            "In neutral atoms, Protons = Electrons."
        ),
        studentTip = "Remember: Neutrons can vary (isotopes) and electrons can be shared or lost (ions), but the proton count never lies—it is the atom's permanent fingerprint."
    ),
    LearnTopic(
        id = "what_is_atomic_mass",
        title = "What is Atomic Mass (A)?",
        iconName = "FitnessCenter",
        summary = "The weighted average mass of an element's naturally occurring isotopes, measured in unified atomic mass units (u).",
        content = "Atomic mass is the mass of an atom, predominantly determined by the combined total of its protons and neutrons inside the dense nucleus. Electrons have such tiny mass (about 1/1,836th of a proton) that they contribute almost negligibly to the total mass.\n\nBecause most elements exist in nature as a mixture of different isotopes (atoms of the same element with different numbers of neutrons), the atomic mass shown on the periodic table is a weighted average based on natural abundance.",
        keyTakeaways = listOf(
            "Protons and neutrons each weigh approximately 1 atomic mass unit (u or Da).",
            "Atomic mass is a weighted average of natural isotopes, which is why it often contains decimal fractions (e.g. Chlorine is 35.45 u).",
            "Mass Number = Protons + Neutrons for a specific isotope."
        ),
        studentTip = "To find the typical number of neutrons in an atom: round the atomic mass to the nearest whole number, then subtract the atomic number!"
    ),
    LearnTopic(
        id = "what_are_groups",
        title = "What are Groups?",
        iconName = "ViewWeek",
        summary = "The 18 vertical columns of the periodic table whose members share similar valence electrons and chemical reactivity.",
        content = "Groups are the 18 vertical columns running top-to-bottom across the periodic table. Elements in the same group share identical valence (outer-shell) electron configurations, which gives them strikingly similar chemical properties and behaviors.\n\nFor example, all Group 1 Alkali Metals have 1 valence electron, make +1 ions, and react violently with water. All Group 18 Noble Gases have filled outer shells, rendering them remarkably unreactive and stable.",
        keyTakeaways = listOf(
            "Groups run vertically (columns 1 through 18).",
            "Elements in the same group are chemical 'families' with similar reactions.",
            "Famous groups: Group 1 (Alkali Metals), Group 2 (Alkaline Earths), Group 17 (Halogens), Group 18 (Noble Gases)."
        ),
        studentTip = "Think of Groups as family trees. Elements in the same column inherit similar 'personalities' because their outer electron shells look the same!"
    ),
    LearnTopic(
        id = "what_are_periods",
        title = "What are Periods?",
        iconName = "TableRows",
        summary = "The 7 horizontal rows indicating the number of electron energy levels (shells) occupied by an atom's electrons.",
        content = "Periods are the 7 horizontal rows running left-to-right across the periodic table. As you move along a period from left to right, each consecutive element gains one more proton in its nucleus and one more electron in its electron cloud.\n\nThe period number (1 to 7) tells you exactly how many principal electron energy shells an atom possesses in its ground state. Period 1 elements have 1 shell, while Period 7 elements have 7 electron shells.",
        keyTakeaways = listOf(
            "Periods run horizontally (rows 1 through 7).",
            "Period number = Total number of occupied electron shells.",
            "Across a period from left to right, metallic character decreases and nonmetallic character increases."
        ),
        studentTip = "Remember: Period = Shells. An element in Period 4 (like Iron or Calcium) distributes its electrons across four main energy levels."
    ),
    LearnTopic(
        id = "metals_and_nonmetals",
        title = "Metals, Nonmetals & Metalloids",
        iconName = "Category",
        summary = "The three major divisions of elements based on electrical conductivity, malleability, and electron-sharing tendencies.",
        content = "The periodic table is broadly divided into three major categories by a diagonal stair-step boundary:\n\n1. METALS (Left & Center, ~80% of elements): Shiny, malleable, ductile, good conductors of heat and electricity. They tend to lose electrons and form positive cations.\n\n2. NONMETALS (Upper Right): Dull, brittle solids or gases, poor conductors of heat and electricity. They tend to gain or share electrons.\n\n3. METALLOIDS (Along the stair-step line): Elements like Silicon and Germanium that have intermediate properties. They act as semiconductors, which makes computer transistors and modern microchips possible!",
        keyTakeaways = listOf(
            "Metals conduct heat & electricity and love to lose electrons.",
            "Nonmetals are insulators and prefer to gain or share electrons.",
            "Metalloids (B, Si, Ge, As, Sb, Te) bridge the gap and power silicon microchips."
        ),
        studentTip = "Gold and Copper are classic metals (shiny, bendable, conduct electricity). Carbon and Oxygen are nonmetals. Silicon is the goldilocks metalloid between them!"
    ),
    LearnTopic(
        id = "what_are_valence_electrons",
        title = "What are Valence Electrons?",
        iconName = "Grain",
        summary = "The electrons residing in the outermost energy level that determine how atoms bond and react.",
        content = "Valence electrons are the electrons located in the outermost principal energy shell of an atom. They are the only electrons directly involved in forming chemical bonds with other atoms.\n\nAtoms strive to reach an energetic stability known as the 'Octet Rule'—a full outer shell of 8 valence electrons (or 2 for Helium). To achieve this stable octet, atoms will transfer electrons (ionic bonding) or share electrons (covalent bonding).",
        keyTakeaways = listOf(
            "Valence electrons live in the outermost electron shell.",
            "They dictate bonding: whether an atom gives, takes, or shares electrons.",
            "Elements with 8 valence electrons (Noble gases) are virtually inert."
        ),
        studentTip = "For representative elements (Groups 1, 2, and 13-18), the last digit of the group number equals the number of valence electrons! (Group 1 = 1, Group 14 = 4, Group 17 = 7)."
    ),
    LearnTopic(
        id = "electron_configuration",
        title = "What is Electron Configuration?",
        iconName = "ScatterPlot",
        summary = "The mathematical address book describing the distribution of electrons in atomic orbitals (s, p, d, f).",
        content = "Electron configuration describes the arrangement of electrons within the shells and subshells of an atom. Subshells are designated by letters: s, p, d, and f.\n\nEach subshell holds a maximum capacity:\n• s holds up to 2 electrons\n• p holds up to 6 electrons\n• d holds up to 10 electrons\n• f holds up to 14 electrons\n\nElectrons fill orbitals in order of increasing energy according to the Aufbau principle, starting from the lowest 1s orbital and working outwards.",
        keyTakeaways = listOf(
            "Subshells have capacities: s (2), p (6), d (10), f (14).",
            "Written notation shows shell number, subshell letter, and electron count (e.g. 1s² 2s² 2p⁶).",
            "The blocks of the periodic table (s-block, p-block, d-block, f-block) correspond directly to which subshell is being filled."
        ),
        studentTip = "Look at the periodic table blocks! The first two columns are the s-block, the middle 10 are the d-block, the right 6 are the p-block, and the bottom rows are the f-block."
    )
)
