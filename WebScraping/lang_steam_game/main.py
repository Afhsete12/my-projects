from datetime import datetime

import requests
from bs4 import BeautifulSoup

COMMON_ID = "td.ellipsis"

PROCESSOR = "i3-5005U"
GRAPHICS = "HD Graphics 5500"


def main():
    response = requests.get("https://run.mocky.io/v3/37a3b85c-046a-4b28-acb2-68b5d75d85ed")
    list_games = response.json()["games"]
    s = requests.Session()
    s.headers.update({
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/108.0.0.0 Safari/537.36'
    })

    for i in range(len(list_games)):
        extractLangOfGame(s, list_games[i])


def find_sp_lang(data):
    find = False
    for lang in data:
        if "Spanish" in lang.text:
            find = True
            break
    return find


def extractLangOfGame(session, g):
    url = f"https://store.steampowered.com/app/{g['app_id']}/"
    resp = session.get(url)
    soup = BeautifulSoup(resp.text, "html.parser")
    data = soup.findAll("td", {"class": "ellipsis"})
    if find_sp_lang(data) is False:
        print(f"Is {g['name']} in Spanish?: {find_sp_lang(data)}")


if __name__ == '__main__':
    main()
