#include "duLinkedList.h"
#include<stdio.h>
#include<stdlib.h>

/**
 *  @name        : Status InitList_DuL(DuLinkedList *L)
 *	@description : initialize an empty linked list with only the head node
 *	@param		 : L(the head node)
 *	@return		 : Status
 *  @notice      : None
 */
Status InitList_DuL(DuLinkedList *L) {
    int n=0,i,j,c,z;                              //LӦ��Ϊָ���ָ�룬�������ܸı䴫������ָ���ָ�򣨵�ַ��
    char a[20];
    DuLNode* p1, * p2;
    *L = (DuLinkedList)malloc(sizeof(DuLNode));
    p1 = p2 = (DuLNode*)malloc(sizeof(DuLNode));
    printf("��������(����������������0��Ϊ����)\n");
    while(1){
    scanf("%s",a);
    for(i=0,j=0;i<strlen(a);i++)                  //���������ַ���
    {
        if(a[i]<=57&&a[i]>=48)                  //0~9��ASCII����48~57
            j++;
    }
    if(j==strlen(a)){                           //��ֹ��������������
        p1->data=0;
        for(c=1;j>0;j--){
            z=(int)a[j-1]-48;
            p1->data+=z*c;
            c*=10;
        }
        break;
    }
    else
        printf("����ȷ����������\n");
    }
    while (p1->data != 0) {
        n++;
        if (n == 1){
            (*L)->next = p1;
            (*L)->prior=NULL;
            p1->prior=(*L);
        }
        else{
            p1->prior=p2;
            p2->next = p1;
        }
        p2 = p1;
        p1 = (DuLNode*)malloc(sizeof(DuLNode));
        while(1){
            scanf("%s",a);
            for(i=0,j=0;i<strlen(a);i++)                  //���������ַ���
            {
                if(a[i]<=57&&a[i]>=48)                  //0~9��ASCII����48~57
                    j++;
            }
            if(j==strlen(a)){                           //��ֹ��������������
                p1->data=0;
                for(c=1;j>0;j--){
                    z=(int)a[j-1]-48;
                    p1->data+=z*c;
                    c*=10;
                }
                break;
            }
            else
                printf("����ȷ����������\n");
            }
    }
    p2->next = NULL;
    printf("��������ɹ���");
    return SUCCESS;
}

/**
 *  @name        : void DestroyList_DuL(DuLinkedList *L)
 *	@description : destroy a linked list
 *	@param		 : L(the head node)
 *	@return		 : status
 *  @notice      : None
 */
void DestroyList_DuL(DuLinkedList *L) {
    DuLNode* p1 = *L;
    DuLNode* p2;
    while (p1 != NULL)
    {
        p2 = p1;
        p1 = p1->next;
        free(p2);
    }
    printf("����������\n");
}

/**
 *  @name        : Status InsertBeforeList_DuL(DuLNode *p, LNode *q)
 *	@description : insert node q before node p
 *	@param		 : p, q
 *	@return		 : status
 *  @notice      : None
 */
Status InsertBeforeList_DuL(DuLNode *p, DuLNode *q) {
    q->next = p;
    q->prior =p->prior;
    p->prior->next=q;
    p->prior=q;
    printf("����ɹ���\n");
    return SUCCESS;
}

/**
 *  @name        : Status InsertAfterList_DuL(DuLNode *p, DuLNode *q)
 *	@description : insert node q after node p
 *	@param		 : p, q
 *	@return		 : status
 *  @notice      : None
 */
Status InsertAfterList_DuL(DuLNode *p, DuLNode *q) {
    if(p->next==NULL){
        q->prior=p;                    //��ֹ�������������һ���ڵ�
        q->next=NULL;
        p->next=q;
    }
    else{
    q->prior=p;
    q->next=p->next;
    p->next->prior=q;
    p->next=q;
    }
    printf("����ɹ���\n");
    return SUCCESS;
}

/**
 *  @name        : Status DeleteList_DuL(DuLNode *p, ElemType *e)
 *	@description : delete the first node after the node p and assign its value to e
 *	@param		 : p, e
 *	@return		 : status
 *  @notice      : None
 */
Status DeleteList_DuL(DuLNode *p, ElemType *e) {
    DuLNode* q = p->next;
    if(p->next->next==NULL){
        p->next = q->next;
    }
    else{
    p->next = q->next;
    q->next->prior=p;
    }
    e = q->data;
    printf("ɾ���ɹ���\n");
    printf("ɾ���Ľڵ��ֵΪ��%d\n",e);
    free(q);
    return SUCCESS;
}

/**
 *  @name        : void TraverseList_DuL(DuLinkedList L, void (*visit)(ElemType e))
 *	@description : traverse the linked list and call the funtion visit
 *	@param		 : L(the head node), visit
 *	@return		 : Status
 *  @notice      : None
 */
void TraverseList_DuL(DuLinkedList L, void (*visit)(ElemType e)) {
    int i = 0;
    DuLinkedList p =NULL;
    p=L->next;
    while (p != NULL) {
        if(p->next==NULL)
            printf("%d\n", p->data);
        else
            printf("%d->", p->data);
        p = p->next;
        i++;
    }
    visit(i);
}
