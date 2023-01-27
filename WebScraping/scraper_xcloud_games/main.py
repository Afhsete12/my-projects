import requests
from bs4 import BeautifulSoup
import uvicorn
from fastapi import FastAPI

app = FastAPI()

games = []

games_currently = 393


def testGossip(s):
    all_games = []
    for i in range(1, 5):
        url = f"https://www.trueachievements.com/xbox-cloud-gaming/games?page={i}"
        resp = s.get(url)
        soup = BeautifulSoup(resp.text, "html.parser")
        get_games = soup.findAll("td", attrs={"class": "game"})

        for game in get_games:
            all_games.append(game.text)
    unique_games = list(set(all_games))
    return unique_games


def main():
    s = requests.Session()
    s.headers.update({
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/108.0.0.0 Safari/537.36'
    })
    global games
    games = testGossip(s)


if games_currently < len(games):
    @app.get("/")
    async def read_root():
        global games
        return games

if __name__ == '__main__':
    main()
    if games_currently < len(games):
        uvicorn.run(app, host="localhost", port=8010)
    else:
        print("No new games")
