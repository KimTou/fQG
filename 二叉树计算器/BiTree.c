#include <stdio.h>
#include <stdlib.h>
#include"BiTree.h"
#include<string.h>
#define LEN 200

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
