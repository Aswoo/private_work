#include <iostream>
//��� ������ ����
/*
	1742��, ������ �Ƹ��߾� ���а� ũ����Ƽ�� ������� �����ϸ�Ʈ ���Ϸ����� ������ ���� ������ �����ϴ� ������ ���´�.
	4���� ū ��� ¦���� �� Ȧ�� �Ҽ��� ������ ��Ÿ�� �� �ִ�.
	���� ��� 8�� 3 + 5�� ��Ÿ�� �� �ְ�, 3�� 5�� ��� Ȧ���� �Ҽ��̴�. ��, 20 = 3 + 17 = 7 + 13, 42 = 5 + 37 = 11 + 31 = 13 + 29 = 19 + 23 �̴�.
	�� ������ ������ �ذ���� ���� �����̴�.
	�鸸 ������ ��� ¦���� ���ؼ�, �� ������ �����ϴ� ���α׷��� �ۼ��Ͻÿ�.
*/
using namespace std;
const int MAX = 1000000;
int pn;
bool check[MAX+1];
int main() {

	check[0] = check[1] = true;
	for (int i = 2;i*i < MAX;i++)
	{
		if (check[i] == false)
		{
			for (int j = i + i;j <= MAX;j += i) {
				check[j] = true;
			}
		}
	}

	int n = 1;
	while (n != 0)
	{
		cin >> n;
		for (int i = 2;i < n;i++)
		{
			if (check[i] == false && check[n - i] == false)
			{
					cout << n << "=" << i << "+" << n - i << endl;
					break;
			}
		}
	}
	

	//system("pause");

	return 0;
}