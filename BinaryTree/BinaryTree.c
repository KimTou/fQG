#include <stdio.h>
#include <stdlib.h>
#include"BinaryTree.h"
#include<string.h>
#define LEN 200

//链式队列结构
typedef struct node
{
    BiTNode *data;                   //数据域指针
    struct node *next;            //指向当前结点的下一结点
} Node;
typedef struct Lqueue
{
    Node *front;                   //队头
    Node *rear;                    //队尾
    size_t length;            //队列长度
} LQueue;


//操作结果：构造空二叉树T
/**
 *  @name        : Status InitBiTree(BiTree T);
 *  @description : 构造空二叉树T
 *  @param       : 二叉树根结点T
 */
Status InitBiTree(BiTree *T)
{
    *T=NULL;
    return SUCCESS;
}

//初始条件：二叉树T存在
//操作结果：摧毁二叉树T
/**
 *  @name        : Status DestroyBiTree(BiTree T);
 *  @description : 摧毁二叉树T
 *  @param       : 二叉树根结点T
 */
Status DestroyBiTree(BiTree T)
{
    if(NULL==T){
        return ERROR;
    }
    DestroyBiTree(T->lchild);         //使用后序遍历，先销毁左、右子树，再释放根结点
    DestroyBiTree(T->rchild);
    free(T);
    return SUCCESS;
}

int n=-1;
//初始条件： definition给出二叉树的定义
//操作结果：按definition构造二叉树T
/**
 *  @name        : Status CreateBiTree(BiTree T, char* definition);
 *  @description : 构造二叉树T
 *  @param       : 二叉树根结点T, 二叉树先序遍历字符串definition
 */
Status CreateBiTree(BiTree *T, char* definition)       //非计算器建树
{
    n++;                 //指针移动至下一位
    if(n>=strlen(definition)-1){
        n=-1;            //为下一次重新构造二叉树做准备
        return ERROR;
    }
    if(definition[n]=='#'){
        *T=NULL;               //若为# 则该节点为空
        return SUCCESS;
    }
    *T=(BiTree)malloc(sizeof(BiTNode));
    (*T)->data=definition[n];
    CreateBiTree(&(*T)->lchild,definition);
    CreateBiTree(&(*T)->rchild,definition);
    return SUCCESS;
}

char* CreateBiTree2(BiTree *T, char* definition)       //计算器建树
{
    char ch=*definition;
    *T=(BiTree)malloc(sizeof(BiTNode));
    (*T)->data=ch;
    if(ch>='0'&&ch<='9'){
        (*T)->lchild=NULL;
        (*T)->rchild=NULL;
    }
    else{
        definition=CreateBiTree2(&(*T)->lchild,definition+1);
        definition=CreateBiTree2(&(*T)->rchild,definition+1);
    }
    return definition;
}

//以下部分函数定义未指定参数类型
//初始条件：二叉树T存在，visit为对结点的操作的应用函数
//操作结果：先序遍历T，对每个结点调用visit函数一次且仅一次，一旦visit失败，则操作失败
/**
 *  @name        : Status PreOrderTraverse(BiTree T, Status (*visit)(TElemType e));
 *  @description : 先序遍历二叉树T
 *  @param       : 二叉树根结点T, 对结点的操作函数visit
 */
Status PreOrderTraverse(BiTree T, Status (*visit)(TElemType e))
{
    if(NULL==T){                             //防止空指针异常
        return ERROR;
    }
    visit(T->data);
    PreOrderTraverse(T->lchild,visit);              //递归遍历
    PreOrderTraverse(T->rchild,visit);
    return SUCCESS;
}
/**
 *  @name        : Status InOrderTraverse(BiTree T, Status (*visit)(TElemType e));
 *  @description : 中序遍历二叉树T
 *  @param       : 二叉树根结点T, 对结点的操作函数visit
 */
Status InOrderTraverse(BiTree T, Status (*visit)(TElemType e))	//中序遍历
{
    if(NULL==T){
        return ERROR;
    }
    InOrderTraverse(T->lchild,visit);
    visit(T->data);
    InOrderTraverse(T->rchild,visit);
    return SUCCESS;
}
/**
 *  @name        : Status PostOrderTraverse(BiTree T, Status (*visit)(TElemType e)));
 *  @description : 后序遍历二叉树T
 *  @param       : 二叉树根结点T, 对结点的操作函数visit
 */
Status PostOrderTraverse(BiTree T, Status (*visit)(TElemType e))	//后序遍历
{
    if(NULL==T){
        return ERROR;
    }
    PostOrderTraverse(T->lchild,visit);
    PostOrderTraverse(T->rchild,visit);
    visit(T->data);
    return SUCCESS;
}
void InitLQueue(LQueue *Q)
{
    Q->front=Q->rear=(LQueue*)malloc(sizeof(LQueue));
    Q->front->next=NULL;
    Q->length=0;
}
Status IsEmptyLQueue(const LQueue *Q)
{
    if(Q->front==Q->rear){     //或者Q->length==0
        return SUCCESS;
    }
    return ERROR;
}
Status EnLQueue(LQueue *Q, BiTNode *T)
{
    Node *a=(Node*)malloc(sizeof(Node));
    a->data=T;
    a->next=NULL;
    Q->rear->next=a;
    Q->rear=a;
    Q->length++;
    return SUCCESS;
}
BiTNode* DeLQueue(LQueue *Q)
{
    Node *a;
    BiTNode *p;
    a=Q->front->next;
    p=a->data;
    Q->front->next=a->next;
    if(Q->rear==a){
        Q->rear=Q->front;      //若队列刚好只剩一个元素，则出列后则为空，Q->rear刚好被free掉，需要重新定向
    }
    free(a);
    Q->length--;
    return p;
}
/**
 *  @name        : Status LevelOrderTraverse(BiTree T, Status (*visit)(TElemType e));
 *  @description : 层序遍历二叉树T
 *  @param       : 二叉树根结点T, 对结点的操作函数visit
 */
Status LevelOrderTraverse(BiTree T, Status (*visit)(TElemType e))	//层序遍历
{
    if(NULL==T){
        return ERROR;
    }
    struct LQueue *Q=(LQueue*)malloc(sizeof(LQueue));       //利用队列
    InitLQueue(Q);
    EnLQueue(Q,T);
    while(!IsEmptyLQueue(Q)){
        T=DeLQueue(Q);
        visit(T->data);
        if(T->lchild){
            EnLQueue(Q,T->lchild);
        }
        if(T->rchild){
            EnLQueue(Q,T->rchild);
        }
    }
}
/**
 *  @name        : int Value(BiTree T);
 *  @description : 对构造出的前缀表达式二叉树求值
 *  @param       : 二叉树根结点T
 *  @note        : 可在结点结构体中设置个Tag值标志数字与操作符来构造二叉树，
 *                 可根据需要自行增加操作.
 */
int Value(BiTree T)			//对构造出的前缀表达式二叉树求值
{
    if(T==NULL){
        return -1;
    }
    else if(T->data>='0'&&T->data<='9'){
        return T->data-'0';                   //字符转化为整型
    }
    else if(T->data=='+'){
        return Value(T->lchild)+Value(T->rchild);
    }
    else if(T->data=='-'){
        return Value(T->lchild)-Value(T->rchild);
    }
    else if(T->data=='*'){
        return Value(T->lchild)*Value(T->rchild);
    }
    else if(T->data=='/'){
        return Value(T->lchild)/Value(T->rchild);
    }
}

//非递归遍历

typedef struct SqStack
{
	BiTNode *fh[LEN];    //用于储存二叉树地址，方便遍历的时候追踪到数的地址
	int top;
	int size;
} SqStack;

void initStack(SqStack *s)//初始化栈
{
    //s->fh=(BiTNode*)malloc(LEN*sizeof(BiTNode));
    s->top=-1;
    s->size=LEN;
}

void push(SqStack *s,BiTree fh)//入栈
{
    if(s->top<(s->size-1))
    {
        s->fh[++s->top]=fh;
    }
    else
        printf("栈空间不足！\n");
}
BiTree pop(SqStack *s)          //出栈
{
    return s->fh[s->top--];
}
BiTree TopStack(SqStack *s) //得到栈顶元素
{
    return s->fh[s->top];
}

void PreOrder(BiTree T,Status (*visit)(TElemType e))
{
    SqStack *s=NULL;
    s=(SqStack*)malloc(sizeof(SqStack));
    initStack(s);
    BiTree temp=T;                    //临时指针
    while(temp!=NULL||s->top!=-1){
        while(temp!=NULL){
            visit(temp->data);
            push(s,temp);      //指针T从根结点出发，向左走到底，并依次将指向沿途的左孩子指针入栈
            temp=temp->lchild;
        }
        if(s->top!=-1){
            temp=pop(s);
            temp=temp->rchild;
        }
    }
    free(s);
}


void InOrder(BiTree T,Status (*visit)(TElemType e))
{
    SqStack *s=NULL;
    s=(SqStack*)malloc(sizeof(SqStack));
    initStack(s);
    BiTree temp=T;
    while(temp!=NULL||s->top!=-1){
        while(temp!=NULL){
            push(s,temp);   //指针T从根结点出发，向左走到底，并依次将指向沿途的左孩子指针入栈
            temp=temp->lchild;
        }
        if(s->top!=-1){
            temp=pop(s);
            visit(temp->data);
            temp=temp->rchild;
        }
    }
    free(s);
}

void PostOrder(BiTree T,Status (*visit)(TElemType e))
{
    SqStack *s=NULL;
    s=(SqStack*)malloc(sizeof(SqStack));
    initStack(s);
    BiTNode *temp=T;
    BiTNode *p;                 //记录指针
    do{
        while(temp!=NULL){
            push(s,temp);
            temp=temp->lchild;
        }
        p=NULL;
        while(s->top!=-1){
            temp=TopStack(s);
            if(temp->rchild==p){         //若右孩子等于NULL或者右孩子被访问过了
                visit(temp->data);
                pop(s);                  //输出后弹出栈
                p=temp;                  //记录下刚刚访问的节点
            }
            else{
                temp=temp->rchild;
                break;
            }
        }
    }while(s->top!=-1);
    free(s);
}
