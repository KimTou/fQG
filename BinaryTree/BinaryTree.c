#include <stdio.h>
#include <stdlib.h>
#include"BinaryTree.h"
#include<string.h>
#define LEN 200

//��ʽ���нṹ
typedef struct node
{
    BiTNode *data;                   //������ָ��
    struct node *next;            //ָ��ǰ������һ���
} Node;
typedef struct Lqueue
{
    Node *front;                   //��ͷ
    Node *rear;                    //��β
    size_t length;            //���г���
} LQueue;


//�������������ն�����T
/**
 *  @name        : Status InitBiTree(BiTree T);
 *  @description : ����ն�����T
 *  @param       : �����������T
 */
Status InitBiTree(BiTree *T)
{
    *T=NULL;
    return SUCCESS;
}

//��ʼ������������T����
//����������ݻٶ�����T
/**
 *  @name        : Status DestroyBiTree(BiTree T);
 *  @description : �ݻٶ�����T
 *  @param       : �����������T
 */
Status DestroyBiTree(BiTree T)
{
    if(NULL==T){
        return ERROR;
    }
    DestroyBiTree(T->lchild);         //ʹ�ú���������������������������ͷŸ����
    DestroyBiTree(T->rchild);
    free(T);
    return SUCCESS;
}

int n=-1;
//��ʼ������ definition�����������Ķ���
//�����������definition���������T
/**
 *  @name        : Status CreateBiTree(BiTree T, char* definition);
 *  @description : ���������T
 *  @param       : �����������T, ��������������ַ���definition
 */
Status CreateBiTree(BiTree *T, char* definition)       //�Ǽ���������
{
    n++;                 //ָ���ƶ�����һλ
    if(n>=strlen(definition)-1){
        n=-1;            //Ϊ��һ�����¹����������׼��
        return ERROR;
    }
    if(definition[n]=='#'){
        *T=NULL;               //��Ϊ# ��ýڵ�Ϊ��
        return SUCCESS;
    }
    *T=(BiTree)malloc(sizeof(BiTNode));
    (*T)->data=definition[n];
    CreateBiTree(&(*T)->lchild,definition);
    CreateBiTree(&(*T)->rchild,definition);
    return SUCCESS;
}

char* CreateBiTree2(BiTree *T, char* definition)       //����������
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

//���²��ֺ�������δָ����������
//��ʼ������������T���ڣ�visitΪ�Խ��Ĳ�����Ӧ�ú���
//����������������T����ÿ��������visit����һ���ҽ�һ�Σ�һ��visitʧ�ܣ������ʧ��
/**
 *  @name        : Status PreOrderTraverse(BiTree T, Status (*visit)(TElemType e));
 *  @description : �������������T
 *  @param       : �����������T, �Խ��Ĳ�������visit
 */
Status PreOrderTraverse(BiTree T, Status (*visit)(TElemType e))
{
    if(NULL==T){                             //��ֹ��ָ���쳣
        return ERROR;
    }
    visit(T->data);
    PreOrderTraverse(T->lchild,visit);              //�ݹ����
    PreOrderTraverse(T->rchild,visit);
    return SUCCESS;
}
/**
 *  @name        : Status InOrderTraverse(BiTree T, Status (*visit)(TElemType e));
 *  @description : �������������T
 *  @param       : �����������T, �Խ��Ĳ�������visit
 */
Status InOrderTraverse(BiTree T, Status (*visit)(TElemType e))	//�������
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
 *  @description : �������������T
 *  @param       : �����������T, �Խ��Ĳ�������visit
 */
Status PostOrderTraverse(BiTree T, Status (*visit)(TElemType e))	//�������
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
    if(Q->front==Q->rear){     //����Q->length==0
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
        Q->rear=Q->front;      //�����иպ�ֻʣһ��Ԫ�أ�����к���Ϊ�գ�Q->rear�պñ�free������Ҫ���¶���
    }
    free(a);
    Q->length--;
    return p;
}
/**
 *  @name        : Status LevelOrderTraverse(BiTree T, Status (*visit)(TElemType e));
 *  @description : �������������T
 *  @param       : �����������T, �Խ��Ĳ�������visit
 */
Status LevelOrderTraverse(BiTree T, Status (*visit)(TElemType e))	//�������
{
    if(NULL==T){
        return ERROR;
    }
    struct LQueue *Q=(LQueue*)malloc(sizeof(LQueue));       //���ö���
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
 *  @description : �Թ������ǰ׺���ʽ��������ֵ
 *  @param       : �����������T
 *  @note        : ���ڽ��ṹ�������ø�Tagֵ��־������������������������
 *                 �ɸ�����Ҫ�������Ӳ���.
 */
int Value(BiTree T)			//�Թ������ǰ׺���ʽ��������ֵ
{
    if(T==NULL){
        return -1;
    }
    else if(T->data>='0'&&T->data<='9'){
        return T->data-'0';                   //�ַ�ת��Ϊ����
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

//�ǵݹ����

typedef struct SqStack
{
	BiTNode *fh[LEN];    //���ڴ����������ַ�����������ʱ��׷�ٵ����ĵ�ַ
	int top;
	int size;
} SqStack;

void initStack(SqStack *s)//��ʼ��ջ
{
    //s->fh=(BiTNode*)malloc(LEN*sizeof(BiTNode));
    s->top=-1;
    s->size=LEN;
}

void push(SqStack *s,BiTree fh)//��ջ
{
    if(s->top<(s->size-1))
    {
        s->fh[++s->top]=fh;
    }
    else
        printf("ջ�ռ䲻�㣡\n");
}
BiTree pop(SqStack *s)          //��ջ
{
    return s->fh[s->top--];
}
BiTree TopStack(SqStack *s) //�õ�ջ��Ԫ��
{
    return s->fh[s->top];
}

void PreOrder(BiTree T,Status (*visit)(TElemType e))
{
    SqStack *s=NULL;
    s=(SqStack*)malloc(sizeof(SqStack));
    initStack(s);
    BiTree temp=T;                    //��ʱָ��
    while(temp!=NULL||s->top!=-1){
        while(temp!=NULL){
            visit(temp->data);
            push(s,temp);      //ָ��T�Ӹ��������������ߵ��ף������ν�ָ����;������ָ����ջ
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
            push(s,temp);   //ָ��T�Ӹ��������������ߵ��ף������ν�ָ����;������ָ����ջ
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
    BiTNode *p;                 //��¼ָ��
    do{
        while(temp!=NULL){
            push(s,temp);
            temp=temp->lchild;
        }
        p=NULL;
        while(s->top!=-1){
            temp=TopStack(s);
            if(temp->rchild==p){         //���Һ��ӵ���NULL�����Һ��ӱ����ʹ���
                visit(temp->data);
                pop(s);                  //����󵯳�ջ
                p=temp;                  //��¼�¸ոշ��ʵĽڵ�
            }
            else{
                temp=temp->rchild;
                break;
            }
        }
    }while(s->top!=-1);
    free(s);
}
