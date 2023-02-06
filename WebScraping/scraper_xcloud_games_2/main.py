import requests
from bs4 import BeautifulSoup
import uvicorn
from fastapi import FastAPI

app = FastAPI()

games = []
format_games = []

test = list[dict[str, str]]


def testGossip(s):
    for i in range(1, 14):
        url = f"https://cloudbase.gg/xbox-cloud-games/page/{i}/"
        resp = s.get(url)
        soup = BeautifulSoup(resp.text, "html.parser")
        all_games = soup.findAll("div", attrs={"class": "kt-adv-heading_e70492-39"})

        for game in all_games:
            games.append(game.text)

    return [{'name': item} for item in games]


def main():
    s = requests.Session()
    s.headers.update({
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/108.0.0.0 Safari/537.36'
    })
    global test
    test = testGossip(s)


@app.get("/api/v1/Xcloud/games")
async def read_root():
    global test
    return test


if __name__ == '__main__':
    main()
    uvicorn.run(app, host="localhost", port=8010)
