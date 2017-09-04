Bluetooth 



- 블루투스 기초



블루투스 

 블루투스는 2.4GHz 무선 링크를 통해 데이터를 주고받기 위한 표준이다. 블루투스는 보안 프로토콜이며 단거리,저전력,저비용으로 장치들을 무선 연결할 수 있도록 해줍니다.

특히 단거리(100m 이하) 에서 상대적으로 작은 데이터를 주고 받는데 최고의 프로토콜입니다. 그리고 블루투스는 유선 통신인 시리얼 통신 인터페이스를(Serial communication interfaces)를 무선으로 대체 가능



블루투스 동작 원리



- 블루투스는 unlicensed ISM frequency band - 2.4GHz 주파수 대역에서 동작한다.

- 이 주파수 대역은 ZigBee,Wifi 등도 사용할 수 있는 RF 대역 따라서 블루투스는 다른 ㅍ ㅡ로토콜과 분리되어 동작할 수 있도록 표준이 만들어져 있습니다. 

   [published specifications](https://www.bluetooth.org/en-us/specification/adopted-specifications) 상세 표준링크

마스터(Masters), 슬레이브(Slaves), 피코넷(Piconets)

- 블루투스 네트워크는(보통 piconets으로 불리는) 마스터/슬레이브(master/slaves) 모델을 사용.

- 이 모델은 하나의 마스터 장치가 다수의 슬레이브장치(7개까지) 연결될 수 있다. 슬레이브 장치는 오직 하나의 마스터 장치에만 연결이 가능합니다.

  ​

![5216816c757b7f1f668b4567](http://www.hardcopyworld.com/ngine/aduino/wp-content/uploads/sites/3/2015/09/5216816c757b7f1f668b4567-300x159.png)

- 마스터 장치는 피코넷을 통해 통신을 조율, 마스터 장치는 연결된 어떤 슬레이브 장치에든 데이터를 전송할 수 있고, 요청을 보낼 수도 있습니다. 슬레이브 장치는 마스터와 데이터를 교환할 수 있지만 슬레이브간 통신은 불가하다.



블루투스 주소와 이름 (BlueTooth Addresses and Names)

- 모든 블루투스 장치는 약어로 BD_ADDR 라 불리는  고유한 48-bit(6 bytes) 주소를 가집니다. 이 주소는 항상 12자리 16진수 값으로 표시됩니다. 이 주소의 상위 절반(24bit)은 organization unique identifier(OUI) 라 불리며 제작사를 의미. 하위 절반 (24bit) 는 장치의 고유한 주소

  ​

연결 과정 (Connection Process)

1. Inquiry - 두 개의 블루투스 장치가 서로를 완전히 모르는 상태라면 서로를 찾기위한 과장을 거쳐야한다. 하나의 장치가 inquiry request를 보내면 다른 하나의 장치는 이 request에 대해 주소, 이름 및 기타 정보와 함께 응답해줘야 합니다.

2. Paging(Connecting) - Paging은 두 장치가 연결되기 위한 과정입니다. 연결이 완성되기 전에 각각의 장치는 서로의 주소를 알고 있어야만 합니다. 

3. Connection - Paging 과정이 끝나면 connection 상태가 됩니다. 연결이 된 동안 장치들은 자신의 상태(모드)를 바꿀 수 있다.

   - Activie Mode - 일반적인 연결 상태,장치는 데이터를 전송,수신하는데 참여합니다.
   - Sniff Mode - 절전 모드,Sleep(비활동) 상태를 유지하다 정해진 간격마다 송시신 내용이 있는지 체크
   - Holde Mode - 일시적인 절전 모드. 장치는 정해진 시간동안 sleep 상태에 들어갔다 다시 Active 모드로 돌아옵니다. 마스터 장치가 슬레이브 장치에게 이 모드로 들어가도록 지시 할 수 있습니다.
   - Park Mode - 정지 모드 , 더 깊은 절전 모드로 마스터 장치에서 신호가 있을 때가지 정지모드로 들어가도록 알려준다.

   ​

Bonding and Paring



두 장치가 연결 된 후 특별한 데이터를 교환하면 Bonding 상태를 만들 수 있다.

Bounding 된 장치들은 서로 가까운 거리를 유지할때 자동으로 연결된다. 

Bounding 은 페어링(paring)이라 불리는 과정으로 만들어집니다. 두 장치는 페어링 될 때 서로 주소,이름,프로파일(profiles)을 교환하고 저장해둔다. 또한 comon secret key를 교환해서 향후 Bonding 될때 사용합니다.



 블루투스 프로파일(Bluetooth Profiles)

- 블루투스 프로파일은 블루투스 장치가 어떤 종류의 데이터를 보내는지 명확하게 정의하기 위한,블루투스의 기본 표준위에 더해진 프로토콜입니다. 블루투스 specfications는 블루투스가 어떻게 동작하는지를 설명하고 프로파일은 어떻게 사용되는지를 정의합니다.
- 블루투스 프로파일은 연결되었을 때 장치가 어떻게 동작해야 하는지를 결정.
- 다음은 자주 접하게 되는 불루투스 프로파일들

**Serial Port Profies (SPP)**

블루투스를 이용 serial communication interface(like RS-232 or UART)를 무선으로 대체 하고 싶은 경우 spp 프로파일 사용.

SPP 프로파일은 두장치가 많은 데이터를 교환하는데 초점이 맞추어져 있다.

이 프로파일은 블루투스의 가장 기본적인 프로파일 중 하나, SPP를 이용하면 두 장치는 RX.TX 라인이 마치 유선으로 연결된 것처럼 데이터를 주고 받을 수있다. 



### ands-Free Profile (HFP) and Headset Profile (HSP)

블루투스 이어폰 등에 사용되는 프로파일로 HFP 의 경우는 차량의 hands-free 오디오 시스템에 사용됩니다. HFP는 HSP 를 기반으로 일반적인 폰 인터랙션 (전화 수신/거절, 종료 등)을 추가로 지원하기 위한 내용들이 포함되었습니다.



### Advanced Audio Distribution Profile (A2DP)

블루투스 장치간 오디오를 전송하기 위한 프로파일입니다. HFP, HSP 는 오디오를 양방향으로 전송하지만 A2DP 는 단방향으로만 전송합니다. 대신 오디오의 음직은 훨씬 높습니다. 상세한 내용은 링크([A2DP specification](https://www.bluetooth.org/en-us/specification/adopted-specifications))를 참고하세요.

[![521516b5757b7f33438b4567](http://www.hardcopyworld.com/ngine/aduino/wp-content/uploads/sites/3/2015/09/521516b5757b7f33438b4567-300x192.png)](http://www.hardcopyworld.com/ngine/aduino/wp-content/uploads/sites/3/2015/09/521516b5757b7f33438b4567.png)

대부분의 A2DP 블루투스 모듈은 제한된 오디오 코덱만을 지원합니다. 최소한 SBC (subband codec), MPEG-1, MPEG-2, AAC, ATRAC 정도는 지원할 것입니다.

 

### A/V Remote Control Profile (AVRCP)

Audio/video remote control profile (AVRCP) 는 블루투스 장치를 무선으로 제어하기 위한 프로파일입니다. 오디오 플레이어를 무선으로 제어가 가능하도록 하기위해 A2DP 프로파일과 함께 지원되곤 합니다.  [AVRCP specification](https://www.bluetooth.org/en-us/specification/adopted-specifications)

[![AVRCP Example Configuration](https://cdn.sparkfun.com/assets/5/2/9/5/5/52152ca5757b7fc24a8b4567.png)](https://cdn.sparkfun.com/assets/5/2/9/5/5/52152ca5757b7fc24a8b4567.png)



블루투스 현재 버전 

- 블루투스는 1994년부터 꾸준히 발전되어 가장 최근 블루투스는 Bluetooth v5이다.
- [2016년](https://ko.wikipedia.org/wiki/2016%EB%85%84) [6월 16일](https://ko.wikipedia.org/wiki/6%EC%9B%94_16%EC%9D%BC) [런던](https://ko.wikipedia.org/wiki/%EB%9F%B0%EB%8D%98)에서 개최한 미디어 이벤트를 통해 블루투스 5가 블루투스 SIG에 의해 공식적으로 소개되었다. 새로운 특징은 주로 부상하는 [사물인터넷](https://ko.wikipedia.org/wiki/%EC%82%AC%EB%AC%BC%EC%9D%B8%ED%84%B0%EB%84%B7) 기술에 촛점을 맞춘 것이다. 2017년 4월에 블루투스5가 지원되는 [삼성 갤럭시 S8](https://ko.wikipedia.org/wiki/%EC%82%BC%EC%84%B1_%EA%B0%A4%EB%9F%AD%EC%8B%9C_S8)를 출시했다. 마켓팅을 위해 소숫점 이하의 숫자는 의도적으로 생략했다. 이러한 변화는 사용자 편의를 위한 마켓팅을 단순화시키기 위함이다.[[4\]](https://ko.wikipedia.org/wiki/%EB%B8%94%EB%A3%A8%ED%88%AC%EC%8A%A4#cite_note-4)



참조 문서 or 블로그

-  https://ko.wikipedia.org/wiki/%EB%B8%94%EB%A3%A8%ED%88%AC%EC%8A%A4
- http://www.hardcopyworld.com/ngine/aduino/index.php/archives/2101