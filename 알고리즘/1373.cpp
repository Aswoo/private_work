#include <iostream>
#include <algorithm>
#include <string>

using namespace std;

string change(int);
int main() {

	string ans;
	string binary;
	cin >> binary;
	while (binary.length() % 3 != 0)
	{
		string br = "0";
		br += binary;
		binary = br;
		//binary.append(0,'0');
	}
	unsigned int count = 0;
	string::iterator it = binary.end();
	it--;
	string line = "";
	//cout << binary.length();
	while(count < binary.length())
	{
		
		string s1;
		s1 = *it;
		line += s1;
		
		count++;
		if (count % 3 == 0)
		{
			reverse(line.begin(), line.end());
			int intValue = 
				((int)line.at(0)-48) * (2 * 2) 
				+ ((int)line.at(1)-48) *(2 ) 
				+ ((int)line.at(2)-48) * 1;
			string ch = change(intValue);
			//cout << ch;
			ans += ch;
			//ans += to_string(intValue);
			line = "";
			if(it != binary.begin()) it--;
		}
		else
		{
			if (it != binary.begin()) it--;
		}

	}

	//
	reverse(ans.begin(), ans.end());
	cout << ans << '\n';
	//system("pause");
	
	return 0;
}
string change(int n)
{
	string ans;
	if (n == 0) return "0";
	while (n > 0) {

		int r = n % 8;

		if (r < 10) {

			ans += (char)(r + '0');

		}

		n /= 8;

	}
	
		

	
		return ans;
}