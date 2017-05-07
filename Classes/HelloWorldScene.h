#ifndef __HELLOWORLD_SCENE_H__
#define __HELLOWORLD_SCENE_H__

#include "cocos2d.h"
#include "extensions/cocos-ext.h"


class HelloWorld : public cocos2d::Layer
{
public:
    static cocos2d::Scene* createScene();

    virtual bool init();
	virtual void onEnter();
    Node* Layer;
    
    // a selector callback
    void menuCloseCallback(cocos2d::Ref* pSender);
    void buyDone(Ref * sender, cocos2d::extension::Control::EventType controlEvent);

    
    // implement the "static create()" method manually
    CREATE_FUNC(HelloWorld);
};

#endif // __HELLOWORLD_SCENE_H__
