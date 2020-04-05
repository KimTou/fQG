typedef struct SqStack
{
	char *fh;
	int top;
	int size;
} SqStack;

void initStack(SqStack *s);//初始化栈
void push(SqStack *s,char fh);//入栈
char pop(SqStack *s);//出栈
char TopStack(SqStack *s); //得到栈顶元素
void houzhui(char *a,char *nb); //将中缀表达式转化为后缀表达式

