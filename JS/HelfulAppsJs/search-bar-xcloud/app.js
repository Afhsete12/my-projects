const charactersList = document.getElementById('charactersList');
const searchBar = document.getElementById('searchBar');
let xcGames = [];

searchBar.addEventListener('keyup', (e) => {
    const searchString = e.target.value.toLowerCase();

    const filteredGames = xcGames.filter((game) => {
        return (
            game.name.toLowerCase().startsWith(searchString)
        );
    });
    displayCharacters(filteredGames);
});

const loadCharacters = async () => {
    try {
        const res = await fetch('https://run.mocky.io/v3/2898fa0c-1d9d-4eab-a9bd-c5521750a49d');
        xcGames = await res.json();
        displayCharacters(xcGames);
    } catch (err) {
        console.error(err);
    }
};

const displayCharacters = (games) => {
    const htmlString = games
        .map((game) => {
            return `
            <li class="character">
                <h2>${game.name}</h2>
            </li>
        `;
        })
        .join('');
    charactersList.innerHTML = htmlString;
};

loadCharacters();
