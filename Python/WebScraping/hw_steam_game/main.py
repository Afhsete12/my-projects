from datetime import datetime

import requests
from bs4 import BeautifulSoup

# COMMON_ID = "ul.bb_ul"
COMMON_ID = "div.game_area_sys_req_leftCol"
UNCOMMON_ID = "div.game_area_sys_req_full"
PROCESSOR = "i3-5005U"
GRAPHICS = "HD Graphics 5500"


def main():
    print(f"Your PC: CPU-> {PROCESSOR} & GC-> {GRAPHICS}")
    s = requests.Session()
    s.headers.update({
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/108.0.0.0 Safari/537.36'
    })

    game(s, 281200)


def parse_hw_data(data):
    hardware = data.findAll('li')
    for hw in hardware:
        if "Processor:" in hw.text:
            print(f"{hw.text}")
        if "Graphics:" in hw.text or "Video Card:" in hw.text:
            print(f"{hw.text}")


def game(session, uuid):
    url = f"https://store.steampowered.com/app/{uuid}/"
    print(url)
    resp = session.get(url)
    soup = BeautifulSoup(resp.text, "html.parser")
    data = soup.select_one(COMMON_ID)
    if data is None:
        data = soup.select_one(UNCOMMON_ID)
        parse_hw_data(data)
    else:
        parse_hw_data(data)


if __name__ == '__main__':
    main()
