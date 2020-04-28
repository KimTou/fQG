#include <stdio.h>
#include <stdlib.h>
#include"BiTree.h"
#include<string.h>
#define LEN 200

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
