typedef struct SqStack
{
	char *fh;
	int top;
	int size;
} SqStack;

void initStack(SqStack *s);//��ʼ��ջ
void push(SqStack *s,char fh);//��ջ
char pop(SqStack *s);//��ջ
char TopStack(SqStack *s); //�õ�ջ��Ԫ��
void houzhui(char *a,char *nb); //����׺���ʽת��Ϊ��׺���ʽ

