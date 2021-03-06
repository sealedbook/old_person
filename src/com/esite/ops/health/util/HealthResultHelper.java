package com.esite.ops.health.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HealthResultHelper {

	private static Map<String,String[]> tips = new HashMap<String, String[]>();
	private static Map<String,String[]> resultAnalyze = new HashMap<String,String[]>();
	static {
		resultAnalyze.put("HEART_RATE_RESULT_ANALYZE_LOW", new String[]{
				"心率低于60次/分者（一般在40次/分以上），称为窦性心动过缓。可见于长期从事重体力劳动者、运动员、健康的青年人、老年人；此外，服用治疗心脏药物也有可能引起心率过缓。如心率低于40次/分，应考虑是病理状态。"
				,"心率低于60次／分的人是否会出现症状，与其心跳过缓的频率和引起心跳过缓的原因有关。在安静状态下，成年人的心率若在50～60次／分之间一般是不会出现明显症状的。但是一般人的心率若在40～50次／分之间，就会出现胸闷、乏力、头晕等症状。"
				,"正常人心跳次数是60～100次/分，小于60就称为心动过缓。生理性窦性心动过缓是正常现象，一般心率在50～60次 /分，不用治疗，常见于正常人睡眠中、体力活动较多的人。病理性情况可能是心率低于40次每分钟，这需要长期监测。"
				,"正常人心跳次数是60～100次/分，小于60就称为心动过缓。正常人，特别是长期参加体育锻炼或强体力劳动者，可能每分钟只有50～60次，但精力充沛，无任何不适者则不属于病态。睡眠和害怕也会引起一时性心动过缓。"
				,"成人每分钟心率在60次以下者称心动过缓。但是，经过长期体育锻练或重体力劳动者，虽然每分钟心率只有50～60次，但无任何不适者则不属于病态。如果平时心率正常突降到40次以下且病人自觉不适，建议多次测量后就医。"
		});
		resultAnalyze.put("HEART_RATE_RESULT_ANALYZE_HIGH", new String[]{
				"成人每分钟心率超过100次（一般不超过160次/分）称为窦性心动过速。常见于正常人运动、兴奋、激动、吸烟、饮酒和喝浓茶、咖啡后。也可见于发热、贫血及应用某些药物。如果心率在 160～220次/分，常为病理状态。"
				,"健康成人的正常心率为60～100次/分成人每分钟心率超过100次，称为心动过速。心动过速分生理性和病理性两种。生理性心率过速如体位改变、体力活动、食物消化、情绪焦虑、妊娠、兴奋、恐惧、激动、饮酒、吸烟、饮茶等都可使心率增快。"
				,"成人每分钟心率超过100次，称为心率过速。健康人运动和情绪紧张可引起心动过速。酒、茶、咖啡和药物如异丙肾上腺素和阿托品常引起窦性心动过速。窦性心动过速的病因多为功能性的，也可见于器质性心脏病和心外因素。"
				,"成人每分钟心率超过100次称心动过速。跑步、饮酒、重体力劳动及情绪激动时心律加快为生理性心动过速；高热、贫血、出血、疼痛等疾病引起的心动过速称病理性心动过速。生理性的心动过速可以不必过于紧张，但病理上的心动过速的应到相关医院治疗。"
		});
		resultAnalyze.put("HEART_RATE_RESULT_ANALYZE_IS_OK", new String[]{
				"心率指心脏每分钟内跳动的次数，心率可反映一个人心脏的健康状况。健康成人的正常心率为60～100次/分，大多数为60～80次/分，女性稍快；老年人偏慢。但正常人心率也可稍快或稍慢。"
				,"心率指心脏每分钟内跳动的次数，正常成年人安静时的心率有显著的个体差异，平均在75次/分左右(60—100次/分之间)。心率可因年龄、性别及其它生理情况而不同。在成年人中，女性的心率一般比男性稍快。同一个人，在不同状态下心率都会不同。"
				,"正常人每分钟心跳应在60—100次之间，。不同年龄心跳次数有所不同。60岁以上老年人心跳可减慢，但最慢不能低于每分钟50次，运动员或强体力劳动者心跳次数可在45次左右。心跳次数快慢，可以反映自身心功能情况。"
		});
		
		resultAnalyze.put("RESPIRATORY_RATE_RESULT_ANALYZE_LOW", new String[]{
				"呼吸过缓指呼吸频率低于12次/分而言。可能因为运动量大，从事重体力劳动，肺活量大，呼吸频率较低。呼吸浅慢见于麻醉剂或镇静剂过量和颅内压增高等。老人呼吸频率可低于年轻人。对于呼吸较慢者，可监测呼吸，有不适可咨询医生。"
				,"呼吸过缓指呼吸频率低于12次/分。说明需要氧气量较少或每次呼吸的空气量较大（肺活量较大）。一般情况下呼吸频率低大多数属于每次呼吸的空气量较大，尤其是平时运动量大的人，由于经常大运动量地锻炼，肺活量比一般人大，每次呼吸时吸入的氧气量较多而呼吸频率较低。"
		});
		resultAnalyze.put("RESPIRATORY_RATE_RESULT_ANALYZE_HIGH", new String[]{
				"呼吸过速，指呼吸频率超过20次/分而言。见于发热、疼痛、贫血、甲状腺功能亢进及心力衰竭等。一般体温升高l℃，呼吸大约增加4次/分。当情绪激动或过度紧张时，亦常出现呼吸深快。"
				,"呼吸过速指呼吸频率超过20次/分而言。见于运动后、发热、情绪激动、肥胖者及不经常运动肺活量小的人。如果呼吸较快且没有明显症状可定期连续监测。若有不适可咨询医生。"
		});
		resultAnalyze.put("RESPIRATORY_RATE_RESULT_ANALYZE_IS_OK", new String[]{
				"胸部的一次起伏就是一次呼吸，即一次吸气一次呼气。呼吸率是指每分钟内呼吸的次数。正常人的呼吸率在12~20次/分，小于12次/分为呼吸过缓；大于20次/分为呼吸过速。"
				,"呼吸率是指每分钟内呼吸的次数。正常成人静息状态下，呼吸为12～20次/分，呼吸与脉搏之比为1：4。新生儿呼吸约44次/分，随着年龄的增长而逐渐减慢。"
		});
		resultAnalyze.put("BLOOD_OXYGEN_RESULT_ANALYZE_LOW", new String[]{
				"血氧饱和度小于90%为过低。血氧饱和度过低的原因：老人因组织器官正常衰老，也可出现。在通风不良的室内待的时间较长；贫血的人血氧饱和度也会比别人低一点。患有心脏病和肺功能差的人的血氧饱和度也会低。一般血氧不会过高因为有血红蛋白结合氧的能力有限。"
		});
		resultAnalyze.put("BLOOD_OXYGEN_RESULT_ANALYZE_HIGH", new String[]{});
		resultAnalyze.put("BLOOD_OXYGEN_RESULT_ANALYZE_IS_OK", new String[]{
				"血氧饱和度是指的血液中氧气溶解的饱和度通俗讲血液氧气含量.血氧饱和度的正常范围在90~100之间。"
				,"血氧饱和度是血液中被氧结合的氧合血红蛋白的容量占全部可结合的血红蛋白容量的百分比，即血液中血氧的浓度，一般用来评价血液携带和输送氧气的能力。血氧饱和度的正常范围在90~100之间。"
		});
		
		resultAnalyze.put("PULSE_RATE_RESULT_ANALYZE_LOW", new String[]{
				"脉率小于60次/分为脉率较慢。脉率减慢可能因为年龄增长，如老年人脉率稍低。休息、睡眠时减慢。脉率与心率相等或稍低于心率。房颤或频发期前收缩时脉率也可稍低。若脉率比较慢，定期连续监测，了解情况，一直较低，建议咨询医生。"
				," 脉率偏慢的原因：甲状腺激素分泌过少，降低体温可以使脉搏跳动变慢；严重营养不良也可以使心脏跳动速率下降，从而使脉率下降。也可能会在心脏病侵袭后脉搏跳动慢。许多用来治疗心脏的药剂，都会使心跳速率下降，脉率下降。镇静剂和止痛药也会如此。其他药物的副作用也会如此。"
		});
		resultAnalyze.put("PULSE_RATE_RESULT_ANALYZE_HIGH", new String[]{
				"脉率大于100次/分为脉率较快。可能原因：运动和情绪激动时可使脉率增快。发烧时，人体的脉率会有所增高。女性脉率稍快于男性。如果脉率过快，建议长期连续测量，若有异常可就医询问。"
				,"脉率过快的原因：正常人在剧烈体力活动或精神激动之后,惊吓后,饮酒,吸烟,喝浓茶及服用麻黄素,咖啡因肾上腺素等药物也可引起心脏搏动增强而感心悸,从而脉搏加快。"
		});
		resultAnalyze.put("PULSE_RATE_RESULT_ANALYZE_IS_OK", new String[]{
				"脉率是一分钟内动脉搏动的频率，脉率与心率相等或稍低于心率。健康成人在安静状态下脉率为60～100次/min。脉率的快慢受年龄、性别、运动和情绪等因素的影响。"
				,"脉率是一分钟内动脉搏动的频率，脉率与心率密切相关，一般脉率与心率相等或稍低于心率。健康成人在安静状态下脉率为60～100次/min，脉率的快慢受年龄、性别、运动和情绪等因素的影响。女性脉率较男性高，运动和情绪激动时可使脉率增快，而休息，睡眠则减慢。发烧时，人体的脉率会有所增高。"
		});
		
		resultAnalyze.put("SYSTOLIC_PRESSURE_RESULT_ANALYZE_LOW", new String[]{
				"暂时性血压偏低可能因为腹泻，身体失水，发烧、出汗，大量出汗失水，引起血压降低。补充水分以后，这两种情况的血压下降会快速恢复到正常水平。生理性低血压最为常见。一般认为与遗传和体质瘦弱有关，多见于20-50岁的妇女和老年人。其他由某些疾病或药物引起的低血压，如脊髓空洞症，风湿性心肌病，降压药等。"
				,"生理性低血压状态是指部分健康人群中，其血压测值已达到低血压标准，但无任何自觉症状，经长期随访，除血压偏低外，人体各系统器官无缺血和缺氧等异常，也不影响寿命。，常见于经常从事较大运动量的人群如体育运动员，重体力劳动者，而体型瘦长的年轻妇女。其他可能由于疾病或一些药物使用如心前区隐痛，风湿性心肌病及使用降压药。"
				,"生理性的血压偏低原因可能因为休息不足，加上饮食方面不注意，营养不足也可能因为从事重体力活动者。病理性血压偏低除血压降低外，常伴有一些症状以及某些疾病如无力疲乏、 精神萎靡、 心前区隐痛、 晕厥。"
		});
		resultAnalyze.put("SYSTOLIC_PRESSURE_RESULT_ANALYZE_HIGH", new String[]{
				"暂时性血压偏高，收缩压≥140mmHg和(或)舒张压≥90mmHg。健康人可能因睡眠不足，情绪激动 精神紧张、或体力活动所引起的暂时性血压增高一次大量饮酒，吃盐过多也可能使血压偏高，如果经常处于这种不良习惯可能导致高血压。原因：1.父母有高血压，子女患高血压几率较大，当然和环境如不良生活方式等有关吸烟、酗酒、高血脂、高盐饮食，肥胖、少动、长期精神紧张、激动、焦虑、噪音等因素也会引起高血压的发生。年龄增长也是其危险因素之一，因此应注重预防。2.性别与年龄　女性在更年期以前，患高血压的比例较男性略低，但更年期后则与男性患病率无明显差别，甚至高于男性；不良生活习惯；工作压力过重；性格；遗传；超重或肥胖。"
				,"高血压是一种以动脉压升高为特征，可伴有心脏、血管、脑和肾脏等器官功能性或器质性改变的全身性疾病。收缩压≥140mmHg和(或)舒张压≥90mmHg。原因：1.父母有高血压，子女患高血压几率较大，当然和环境如不良生活方式等有关吸烟、酗酒、高血脂、高盐饮食，肥胖、少动、长期精神紧张、激动、焦虑、噪音等因素也会引起高血压的发生。年龄增长也是其危险因素之一，因此应注重预防。2.性别与年龄　女性在更年期以前，患高血压的比例较男性略低，但更年期后则与男性患病率无明显差别，甚至高于男性；不良生活习惯；工作压力过重；性格；遗传；超重或肥胖。"
		});
		resultAnalyze.put("SYSTOLIC_PRESSURE_RESULT_ANALYZE_IS_OK", new String[]{
				"血压：血压是血液在血管内流动时，作用于血管壁的压力. 正常血压指收缩压应小于或等于140mmHg，舒张压小于或等于90mmHg。血压数值为小于120/80mmHg者,认为是理想血压。血压数值120-139/80-89mmHg,认为是正常高值血压。"
				,"血压指血管内的血液对于单位面积血管壁的侧压力，即压强。当血管扩张时，血压下降;血管收缩时，血压升高。血压数值为小于140/90mmHg者,认为是正常血压.血压数值120-139/80-89mmHg,认为是正常高值血压。"
		});
		
		resultAnalyze.put("DIASTOLIC_PRESSURE_RESULT_ANALYZE_LOW", new String[]{
				"暂时性血压偏低可能因为腹泻，身体失水，发烧、出汗，大量出汗失水，引起血压降低。补充水分以后，这两种情况的血压下降会快速恢复到正常水平。生理性低血压最为常见。一般认为与遗传和体质瘦弱有关，多见于20-50岁的妇女和老年人。其他由某些疾病或药物引起的低血压，如脊髓空洞症，风湿性心肌病，降压药等。"
				,"生理性低血压状态是指部分健康人群中，其血压测值已达到低血压标准，但无任何自觉症状，经长期随访，除血压偏低外，人体各系统器官无缺血和缺氧等异常，也不影响寿命。，常见于经常从事较大运动量的人群如体育运动员，重体力劳动者，而体型瘦长的年轻妇女。其他可能由于疾病或一些药物使用如心前区隐痛，风湿性心肌病及使用降压药。"
				,"生理性的血压偏低原因可能因为休息不足，加上饮食方面不注意，营养不足也可能因为从事重体力活动者。病理性血压偏低除血压降低外，常伴有一些症状以及某些疾病如无力疲乏、 精神萎靡、 心前区隐痛、 晕厥。"
		});
		resultAnalyze.put("DIASTOLIC_PRESSURE_RESULT_ANALYZE_HIGH", new String[]{
				"暂时性血压偏高，收缩压≥140mmHg和(或)舒张压≥90mmHg。健康人可能因睡眠不足，情绪激动 精神紧张、或体力活动所引起的暂时性血压增高一次大量饮酒，吃盐过多也可能使血压偏高，如果经常处于这种不良习惯可能导致高血压。原因：1.父母有高血压，子女患高血压几率较大，当然和环境如不良生活方式等有关吸烟、酗酒、高血脂、高盐饮食，肥胖、少动、长期精神紧张、激动、焦虑、噪音等因素也会引起高血压的发生。年龄增长也是其危险因素之一，因此应注重预防。2.性别与年龄　女性在更年期以前，患高血压的比例较男性略低，但更年期后则与男性患病率无明显差别，甚至高于男性；不良生活习惯；工作压力过重；性格；遗传；超重或肥胖。"
				,"高血压是一种以动脉压升高为特征，可伴有心脏、血管、脑和肾脏等器官功能性或器质性改变的全身性疾病。收缩压≥140mmHg和(或)舒张压≥90mmHg。原因：1.父母有高血压，子女患高血压几率较大，当然和环境如不良生活方式等有关吸烟、酗酒、高血脂、高盐饮食，肥胖、少动、长期精神紧张、激动、焦虑、噪音等因素也会引起高血压的发生。年龄增长也是其危险因素之一，因此应注重预防。2.性别与年龄　女性在更年期以前，患高血压的比例较男性略低，但更年期后则与男性患病率无明显差别，甚至高于男性；不良生活习惯；工作压力过重；性格；遗传；超重或肥胖。"
		});
		resultAnalyze.put("DIASTOLIC_PRESSURE_RESULT_ANALYZE_IS_OK", new String[]{
				"血压：血压是血液在血管内流动时，作用于血管壁的压力. 正常血压指收缩压应小于或等于140mmHg，舒张压小于或等于90mmHg。血压数值为小于120/80mmHg者,认为是理想血压。血压数值120-139/80-89mmHg,认为是正常高值血压。"
				,"血压指血管内的血液对于单位面积血管壁的侧压力，即压强。当血管扩张时，血压下降;血管收缩时，血压升高。血压数值为小于140/90mmHg者,认为是正常血压.血压数值120-139/80-89mmHg,认为是正常高值血压。"
		});
		
	}
	
	static {
		tips.put("HEART_RATE_RESULT_TIPS_LOW", new String[]{
				"宜饮食宜高热量、高维生素而易消化的食物；菌菇类，菠菜芹菜，宜多吃一些富含钾的食物。忌辛辣刺激性食品；忌油腻食物；适当限制蛋白质和热能的摄入少吃葱类，辣椒白酒，红烧肉等参加体育运动要适量，根据自身情况量力而行，可做些如强心保健操、太极拳、骑自行车等运动。"
				,"心跳过慢者在平时应多喝水，多吃富含蛋白质、维生素且易消化的食物，如牛肉、鱼虾、鸡、甲鱼、牛奶等；多食蔬菜及水果如青菜、菠菜、西瓜、西红柿、葡萄、柠檬、梨、苹果等。少吃辛辣刺激性食物，如葱姜蒜、胡椒、酱菜等。应劳逸结合，注意避免忧思过度，多做适合自己的户外运动，这样可以增加肠蠕动。还可以按摩腹部，被动增加肠蠕动，以防止便秘的发生。"
				," 吸烟饮酒是引起心律失常的重要诱发因素，应戒烟忌酒。平时可服用益气养心的药膳，如人参粥、大枣粥、莲子粥等。参加体育运动要适量，根据自身情况量力而行，可做些如强心保健操、太极拳、骑自行车等运动。"
				,"老年人应动、静结合，适当参加力所能及的体力劳动及运动，常听欢快、轻松的音乐，常去鸟语花香处散步。 注意防治大便干燥，保持大便通畅，对心律失常病人非常重要。要注意饮食调整，忌食厚味辛辣食物，多吃蔬菜和水果。，应多吃富含维生素的食物及蔬菜。平时应多饮水，尤在夏季应注意补充体内水份分，有人主张每天至少喝3杯水(约1200毫升)，加上饮食中的水分，每天入水量不宜少于2200毫升。"
				,"不暴饮暴食，多吃新鲜水果、蔬菜。控制体重，不超过标准体重的5%。.避免着凉，预防感冒。保持室内外清洁。不可过量饮酒或常饮高浓度烧酒及吸烟。养成良好的排便习惯。应养成晨起定时大便的习惯。晨起没有便意也要多蹲一会儿，坚持日久，可以形成条件反射，养成定时大便的习惯。经常运动，保持好心情。"
				,"衣服要保暖。，因为寒冷会使得心动过缓的情形加重。所以胸前区不能受到寒冷刺激，一般至少要比别人多穿一件背心。不要等到觉得冷了才添衣服。生活要有规律，一定要遵守定时起床，定时睡觉。良好的作息习惯能让心脏休生养息，对于恢复功能有利。切勿昼夜颠倒，通宵娱乐，或者常夜间工作，这样会让心肌的损伤积重难返。"
				,"老年人出现心动过缓的原因有多种，多数是生理性原因，但是也有病理性原因。老年人出现心动过缓时，既不能漠然置之，也不要过分紧张，应去医院就诊，查清病因，及时治疗。生活上要注意心态平和，与朋友多交流.平时注重锻炼，可以做做家务，浇花，买菜等。饮食上可吃些高热量、高维生素，高纤维而易消化的食物，如金针菇，芹菜，鱼虾类。"
				,"心动过缓者 要听取专科医生的建议，千万不可盲目锻炼。通常首选健身方式是散步，适量步行有利于血液返回心脏，可减轻心脏负担。虽然运动可以使得心率加速，但是那样是强迫心脏做功，对于治疗心动过缓非但无益反而有害。定期测量心率，监测效果，发现问题可咨询医生。"
				,"适宜食用热性的食品，如姜、蒜等。补品中比较合适的是红参。。芳香类调味品如桂皮、胡椒、花椒、茴香以及辣椒，，促使血行顺畅。心跳慢着需要忌口一些的食物：首先要忌烟酒，直至水烟以及啤酒都要忌，不能心存侥幸；其次膏粱厚味，高脂肪和高蛋白的食品都在禁忌之列。例如：甲鱼、鳗、螃蟹之类，即使狗肉、羊肉也不宜过食；第三性凉之品，如生冷瓜果、冷饮、凉菜，尽量少食或不食。"
				,"心跳过慢者注意休息，避免喜怒忧思等精神刺激，.注意生活和情志的调理 应饮食有节、起居有常，不妄作劳室内光线一般不宜过强。保持环境清静，禁止喧哗、嘈杂，养成良好的排便习惯。应养成晨起定时大便的习惯。防止便秘可取蜂蜜2～3匙，加温开水半杯，搅匀后饮用，每日2～3次。定期测量心率，以便及时了解心率变化。"
		});
		tips.put("HEART_RATE_RESULT_TIPS_HIGH", new String[]{
				"要养成按时作息的习惯，保证睡眠。运动要适量，量力而行，不勉强运动或运动过量，不做剧烈及竞赛性活动，可做气功、打太极拳。洗澡水不要太热，洗澡时间不宜过长。养成按时排便习惯，保持大便通畅。饮食要定时定量.不饮浓茶不吸烟。避免着凉，预防感冒。不从事紧张工作，不从事驾驶员工作。"
				,"定期检查身体预防心动过速要注意定期复查心电图，电解质、肝功、甲功等　　专家指出，一旦确诊后病人往往高度紧张、焦虑、忧郁，严重关注，频频求医，迫切要求用药控制心律失常，而完全忽略病因、诱因的防治，常造成喧宾夺主，本末倒置。因此，窦性心动过速还要注意预防诱发因素，如吸烟、酗酒、过劳、紧张、激动、暴饮暴食，消化不良，感冒发烧，摄入盐过多等。"
				,"预防心动过速，要注意精神调节，平时不要激动，不要发火，保持心态平和。要避免精神紧张和过度劳累，做到生活规律、起居有常、精神乐观、情绪稳定均可减少心动过速的发生。忌食辛辣、刺激性食物;戒烟酒、咖啡;食宜清淡。运动要适量，本着“量力而动”的原则，不可勉强运动或过量运动，不能认为运动量越大越有助于健康。中老年人以散步、打太极拳等为宜。"
				,"心动过速，睡前不宜喝咖啡、浓茶等刺激性或提神的饮料。要养成早睡的习惯，也不宜看有刺激性的影视、书刊等运动要适量。避免突然的冷、热刺激；洗澡时水温不宜过高。避免饮食不节、暴饮暴食，多吃新鲜水果、蔬菜。情绪稳定，不要遇事抑郁，更不能暴怒或过分紧张与焦虑。与周围人和睦相处，保持心情舒畅，不要生闷气。"
				,"对于心动过快者首先应尽量避免诱因，如饮浓茶、喝酒及应用兴奋心脏加快心率的药物。保持心情愉快，防止过度激动与焦虑。如有心肺疾病或其他全身性疾病时应积极治疗。对反复发作、症状明显而影响日常生活与工作时，应及时就诊，尽早查明原因，以利防治。"
				,"不要忽视定期检查身体。有的患者的心律失常就是在检查身体时发现的。一旦发现心律失常，应该及时到医院进行必要的处理。如果患者本人感到不适，更应马上到医院检查，不要认为“没事”而掉以轻心。"
				,"情绪稳定，不要遇事抑郁，更不能暴怒或过分紧张与焦虑。与周围人和睦相处，保持心情舒畅，不要生闷气。"
				,"运动要适量，本着“量力而动”的原则，不可勉强运动或过量运动，不能认为运动量越大越有助于健康。中老年人以散步、打太极拳等为宜。"
		});
		
		tips.put("RESPIRATORY_RATE_RESULT_TIPS", new String[]{
				"养成良好的生活习惯，不抽烟。也要避免被动吸烟。平时要注意规律生活和休息，畅情志，避免过度劳累及焦虑、暴怒，均衡饮食，保持大便通畅，限酒。定时出去呼吸一些新鲜空气，每年起码做一次检查等。适当控制体重，避免肥胖。"
				,"加强身体锻炼，增强机体的抵抗力。运动量要根据自己的身体情况而定。每天早晨可散步、打拳、慢跑等，这样能呼吸新鲜空气，促进血液循环，冬季锻炼能提高呼吸道黏膜对冷空气的适应能力。"
				,"合理调节室温，预防感冒，冬季室内温度不宜过高，否则与室外温差大，易患感冒。夏天，不宜贪凉，使用空调温度要适中，否则外出易患热伤风诱发支气管炎发作，流感流行季节，尽量少到人群中去，大量出汗不要突然脱衣，以防受凉，注意随季节改变增减衣服，老年人可注射流感疫苗，减少流感感染机会。"
				,"合理膳食可多摄入一些高纤维素以及新鲜的蔬菜和水果，营养均衡，包括蛋白质、糖、脂肪、维生素、微量元素和膳食纤维等必需的营养素，荤素搭配，食物品种多元化，充分发挥食物间营养物质的互补作用。"
				,"调整日常生活与工作量，有规律地进行活动和锻炼，避免劳累。保持情绪稳定，避免情绪激动和紧张。、保持大便通畅，避免用力大便，多食水果及高纤维素食物。、避免寒冷刺激，注意保暖。"
		});
		
		tips.put("BLOOD_OXYGEN_RESULT_TIPS_LOW", new String[]{
				
		});
		
		tips.put("SYSTOLIC_PRESSURE_RESULT_TIPS_LOW", new String[]{
				"血压属于稍偏低，应该是休息不足，加上饮食方面不注意，营养不足导致的，建议你要注意多休息，保持足够的睡眠时间，饮食上多注意补充营养，荤素搭配，适当增加食盐的量，多吃些高蛋白的饮食。加强体育锻炼，提高机体调节功能；建议长期监测血压，若血压长期偏低，建议去医院做详细检查。"
				,"饮食营养方面应给予高营养、易消化和富含维生素的饮食:适当补充维生素；适当饮用浓茶，有助于提高中枢神经系统的兴奋性，改善血管舒缩中枢功能，有利于提升血压和改善临床症状；适当参加运动和医疗体育如医疗体操、保健操、太极拳、等有助于改善心肺功能的活动。"
				,"血压偏低者饮食应荤素搭配：桂圆、莲子、大枣、桑椹等，具有健神补脑之功，宜经常食用，增强体质；由失血引起的低血压，应注意进食提供造血原料的食物，如富含蛋白质、铜、铁元素的食物——肝类、鱼类、奶类、蛋类、豆类以及含铁多的蔬菜水果等。 低血压病人宜选择高钠（食盐每日宜12－15克）、高胆固醇的饮食，如动物脑、肝、蛋黄、奶油、鱼子等。忌食生冷及寒凉、破气食物，如菠菜、萝卜、芹菜、冷饮等。"
				,"加强营养，荤素兼吃，合理搭配膳食，保证摄入全面充足的营养物质，使体质从纤弱逐渐变得健壮。 多食补气血、温补脾肾的食物，如莲子、桂圆、大枣、桑椹等果品。多喝汤，多饮水，增加盐份摄入 2、若伴有红细胞计数过低，血红蛋白不足的贫血症，宜适当多吃富含蛋白质、铁等(造血原料)的食物，诸如猪肝、瘦肉、牛奶、鱼虾、大豆、豆腐、红糖及新鲜蔬菜、水果。纠正贫血，有利于增加心排血量，改善大脑的供血量，提高血压和消除血压偏低引起的不良症状。"
				,"血压低，要多喝水，多吃汤类食品，每日食盐略多于常人；注意营养，多吃水果蔬菜 ；平时出门的时候带点糖果，不舒服的时候吃点会有好处的 ；生活，作息，饮食有节制有规律；多运动但不要过渡运动；保持心情舒畅。"
				,"低血压患者日常生活预防常识 ：晚上睡觉将头部垫高，可减轻低血压症状；锻炼身体，增强体质。平时养成运动的习惯，均衡的饮食，培养开朗的个性，保证足够的睡眠、规律正常的生活；早上起床时，应缓慢地改变体位，不可用力过猛，防止血压突然下降；不要在闷热或缺氧的环境中站立过久，以减少发病。低血压患者轻者如无任何症状，无需药物治疗。重者伴有明显症状，必须给予积极治疗，改善症状，提高生活质量，防止严重危害发生。"
				,"低血压病人宜选择高钠（食盐每日宜12－15克）、高胆固醇的饮食，如动物脑、肝、蛋黄、奶油、鱼子等，使血容量增加，心排血量也随之增加，动脉紧张度增强，血压将随之上升。 忌食生冷及寒凉、破气食物，如菠菜、萝卜、芹菜、冷饮等。 "
				,"饮食应高热量高蛋白：在六大类食物均衡摄取的前提下，体重过轻或营养不良所导致的血压较低，每日摄取热量建议每公斤体重35大卡，蛋白质则以每公斤体重1.2-1.5克。食欲较差者，可选择能刺激食欲的食物和调味品，如葱、姜、蒜、糖、胡椒盐、辣椒等，且以少量多餐的方式来增加热量及蛋白质摄取量。"
				,"搭配运动调整体质：加强运动量可以帮助身体调节血压，因此血压低的民众可以藉由适度的运动来促进身体的血液循环，进而帮助血压的调节。高盐饮食：血压低者可经增加盐分摄取量提高血压，约为正常的2至3倍，每日约10至15克，且多喝水增加体内血容量。红血球过低导致贫血：如果是因为红血球过低及血红素过低所造成的低血压，宜摄取一定量的动物性蛋白质，如肉、蛋、奶、鱼类，补充适量维生素B12及维生素C等。植物性蛋白质则建议以大豆、豆腐，并搭配新鲜蔬菜及水果。"
		});
		tips.put("SYSTOLIC_PRESSURE_RESULT_TIPS_HIGH", new String[]{
				"饮食要清淡，血压偏高者应多吃粗粮、杂粮、新鲜蔬菜和水果、豆制品、瘦肉、鱼、鸡等食物，少吃动物油脂和油腻食品，少吃糖、浓茶、咖啡等刺激性食品。限制食盐量,控制钠盐摄入量有利于降低和稳定血压。根据世界卫生组织的推荐，每人每日以不超过6g为宜。多吃含钾、钙丰富而含钠低的食物，含钾丰富的食物有：土豆、芋头、茄子、莴笋、海带、冬瓜、西瓜、柑橘等。含钙丰富的食物有：牛奶、酸奶、虾皮、芝麻酱、绿色蔬菜等。戒烟戒酒,适当运动。"
				,"饮食应节制 一日三餐定时定量，要少吃盐，不要暴饮暴食，吃饭以八分饱为宜。血压偏高者在饮食中应适量吃一些能降低血压的食物，如：黑木耳、荸荠、芹菜、葫芦、绿豆、莲子心等。多吃豆类，豆类含钾都十分丰富，大豆制品中的豆腐含钙和镁也较为丰富。因此，豆类及豆制品是高血压病人每天都应当吃的食物。成年人以200～300克/日为宜。多吃蔬菜，而芹菜是绿叶蔬菜中降血压的典型代表。通常越是颜色深的绿色蔬菜，钾、钙、镁含量越高。"
				,"要少吃盐，这是关键。因为，食盐越多，血压升高的机率越大。要适量增加含每天食盐不应超过6克。，因为体内缺钙容易导致高血压，缺镁则易使血管变得硬化，所以要适量增加富含钾、钙、镁元素的食物。含镁高的食物有豆制品、牛奶、蚌壳、贝类食物。富含钙质的食物比较多，如海带、芝麻酱、山楂、虾米皮、绿叶蔬菜、雪里红、榨菜、鱼类、排骨和猪手等等。"
				,"要增加含维生素和矿物质的食物摄入，如水果、蔬菜和瘦肉等。这些食品中含有钾、维生素C、亚油酸等，它们能扩张血管，有利血管变得柔软，降低交感神经的活性，使血压降低，运转正常。适当增加不饱和脂肪酸的摄入，要减少动物脂肪的摄入，少吃含糖类食物，食糖过多则易升高甘油三酯，使血液粘度增大，并使血钙降低，不利于降低血压。应当戒烟戒酒。不能再吸烟饮酒，适当运动如打太极，散步养花等。"
				,"血压偏高的人饮食需要适时定量，不饥不饱，不暴饮暴食。食盐摄取每天应该限制在5克以下。浮肿明显时，更应该严格控制食盐。但长期低盐或者缺盐，可导致食欲不振，全身乏力等现象，所以不能无盐。血压偏高的人要多吃些含钾丰富的食物。如油菜、菠菜、小白菜及西红柿等。总之，血压高者的饮食应以清淡为主，宁素勿荤，宁淡勿浓，宁饥勿饱，生活上做到调情志，益肾精，慎饮食。"
				,"运动对高血压的重要性：有氧运动同减肥一样可以降低血压，如散步、慢跑、太极拳、骑自行车和游泳都是有氧运动。进行运动要注意：① 勿过量或太强太累，② 注意周围环境气候：夏天：避免中午艳阳高照的时间;冬天：要注意保暖 ③ 穿着舒适吸汗的衣服：④ 选择安全场所：如公园、学校，勿在巷道、马路边。⑤ 进行运动应在饭后2小时，切勿空腹，以免发生低血糖。同时注意饮食调节多吃水果蔬菜，清淡少盐低脂饮食。"
				,"血压正常高值根据我国流行病学统计血压数值120-139/80-89mmHg,十年后心血管发生危险度比110/75mmHg高1倍以上，患高血压概率增加45%~64%。可限制食盐摄入、多食绿叶蔬果和脱脂牛奶补充钙和钾盐、减少酒精摄入量、减少饱和脂肪摄入量和脂肪总量少吃肥肉，心脏和动物肝脏。减轻精神压力，保持心理平衡多听歌，多和朋友交流。减轻体重、增加运动例如散步，养花等。定期检测血压，以免发展成高血压。"
				,"临界高血压边缘型高血压 应进行科学的适量的锻炼， 以太极拳、步行、慢跑、保健气功（放松功）、医疗保健体操（如降压静心操）等比较合适；合理的饮食，良好的休息时间及乐观的心情。临界高血压患者的锻炼内容以放松性和耐力性练习为主。 减轻体重，限制食盐的摄入量，并且定期监测血压，必要时就医。"
				,"体检时发现为临界高血压者，需连续每日两次测血压共一周，观察血压变化;若一年之后，血压均值升高10毫米汞柱，就需要用药物治疗了。平时生活中可注意以下几点一是控制体重、减少脂肪摄入，约有80%的临界高血压者可通过减肥使血压降至正常;二是戒烟限酒，这是控制临界高血压发展的重要措施;三是限制盐、糖摄入，多数患者可通过这一措施使血压得到控制;四是增加钙、钾的摄入，如多吃虾皮、牛奶、鱼类、豆类、绿叶蔬菜、桔子、香蕉等食物;五是坚持运动，进行体育锻炼是临界高血压者降压的有效手段，患者宜长期坚持慢跑、快走、体操、太极拳等运动;六是保持心理平衡、防止情绪失控，有助于高血压恢复正常。"
				,"盐的摄入量与高血压呈正相关，即人群中盐摄入越多血压水平就越高。世界卫生组织规定每人每天摄盐量不得超过6克，这里的6克不仅指食盐，还包括味精、酱油等含盐调料和食品中的盐量。除听从医嘱服用适当的药物外，还要注意劳逸结合、注意饮食、适当运动、保持情绪稳定、睡眠充足。老年人降压不能操之过急，血压宜控制在140～159mmhg为宜，减少心脑血管并发症的发生。"
		});
		
		tips.put("DIASTOLIC_PRESSURE_RESULT_TIPS_LOW", new String[]{
				"血压属于稍偏低，应该是休息不足，加上饮食方面不注意，营养不足导致的，建议你要注意多休息，保持足够的睡眠时间，饮食上多注意补充营养，荤素搭配，适当增加食盐的量，多吃些高蛋白的饮食。加强体育锻炼，提高机体调节功能；建议长期监测血压，若血压长期偏低，建议去医院做详细检查。"
				,"饮食营养方面应给予高营养、易消化和富含维生素的饮食:适当补充维生素；适当饮用浓茶，有助于提高中枢神经系统的兴奋性，改善血管舒缩中枢功能，有利于提升血压和改善临床症状；适当参加运动和医疗体育如医疗体操、保健操、太极拳、等有助于改善心肺功能的活动。"
				,"血压偏低者饮食应荤素搭配：桂圆、莲子、大枣、桑椹等，具有健神补脑之功，宜经常食用，增强体质；由失血引起的低血压，应注意进食提供造血原料的食物，如富含蛋白质、铜、铁元素的食物——肝类、鱼类、奶类、蛋类、豆类以及含铁多的蔬菜水果等。 低血压病人宜选择高钠（食盐每日宜12－15克）、高胆固醇的饮食，如动物脑、肝、蛋黄、奶油、鱼子等。忌食生冷及寒凉、破气食物，如菠菜、萝卜、芹菜、冷饮等。"
				,"加强营养，荤素兼吃，合理搭配膳食，保证摄入全面充足的营养物质，使体质从纤弱逐渐变得健壮。 多食补气血、温补脾肾的食物，如莲子、桂圆、大枣、桑椹等果品。多喝汤，多饮水，增加盐份摄入 2、若伴有红细胞计数过低，血红蛋白不足的贫血症，宜适当多吃富含蛋白质、铁等(造血原料)的食物，诸如猪肝、瘦肉、牛奶、鱼虾、大豆、豆腐、红糖及新鲜蔬菜、水果。纠正贫血，有利于增加心排血量，改善大脑的供血量，提高血压和消除血压偏低引起的不良症状。"
				,"血压低，要多喝水，多吃汤类食品，每日食盐略多于常人；注意营养，多吃水果蔬菜 ；平时出门的时候带点糖果，不舒服的时候吃点会有好处的 ；生活，作息，饮食有节制有规律；多运动但不要过渡运动；保持心情舒畅。"
				,"低血压患者日常生活预防常识 ：晚上睡觉将头部垫高，可减轻低血压症状；锻炼身体，增强体质。平时养成运动的习惯，均衡的饮食，培养开朗的个性，保证足够的睡眠、规律正常的生活；早上起床时，应缓慢地改变体位，不可用力过猛，防止血压突然下降；不要在闷热或缺氧的环境中站立过久，以减少发病。低血压患者轻者如无任何症状，无需药物治疗。重者伴有明显症状，必须给予积极治疗，改善症状，提高生活质量，防止严重危害发生。"
				,"低血压病人宜选择高钠（食盐每日宜12－15克）、高胆固醇的饮食，如动物脑、肝、蛋黄、奶油、鱼子等，使血容量增加，心排血量也随之增加，动脉紧张度增强，血压将随之上升。 忌食生冷及寒凉、破气食物，如菠菜、萝卜、芹菜、冷饮等。 "
				,"饮食应高热量高蛋白：在六大类食物均衡摄取的前提下，体重过轻或营养不良所导致的血压较低，每日摄取热量建议每公斤体重35大卡，蛋白质则以每公斤体重1.2-1.5克。食欲较差者，可选择能刺激食欲的食物和调味品，如葱、姜、蒜、糖、胡椒盐、辣椒等，且以少量多餐的方式来增加热量及蛋白质摄取量。"
				,"搭配运动调整体质：加强运动量可以帮助身体调节血压，因此血压低的民众可以藉由适度的运动来促进身体的血液循环，进而帮助血压的调节。高盐饮食：血压低者可经增加盐分摄取量提高血压，约为正常的2至3倍，每日约10至15克，且多喝水增加体内血容量。红血球过低导致贫血：如果是因为红血球过低及血红素过低所造成的低血压，宜摄取一定量的动物性蛋白质，如肉、蛋、奶、鱼类，补充适量维生素B12及维生素C等。植物性蛋白质则建议以大豆、豆腐，并搭配新鲜蔬菜及水果。"
		});
		tips.put("DIASTOLIC_PRESSURE_RESULT_TIPS_HIGH", new String[]{
				"饮食要清淡，血压偏高者应多吃粗粮、杂粮、新鲜蔬菜和水果、豆制品、瘦肉、鱼、鸡等食物，少吃动物油脂和油腻食品，少吃糖、浓茶、咖啡等刺激性食品。限制食盐量,控制钠盐摄入量有利于降低和稳定血压。根据世界卫生组织的推荐，每人每日以不超过6g为宜。多吃含钾、钙丰富而含钠低的食物，含钾丰富的食物有：土豆、芋头、茄子、莴笋、海带、冬瓜、西瓜、柑橘等。含钙丰富的食物有：牛奶、酸奶、虾皮、芝麻酱、绿色蔬菜等。戒烟戒酒,适当运动。"
				,"饮食应节制 一日三餐定时定量，要少吃盐，不要暴饮暴食，吃饭以八分饱为宜。血压偏高者在饮食中应适量吃一些能降低血压的食物，如：黑木耳、荸荠、芹菜、葫芦、绿豆、莲子心等。多吃豆类，豆类含钾都十分丰富，大豆制品中的豆腐含钙和镁也较为丰富。因此，豆类及豆制品是高血压病人每天都应当吃的食物。成年人以200～300克/日为宜。多吃蔬菜，而芹菜是绿叶蔬菜中降血压的典型代表。通常越是颜色深的绿色蔬菜，钾、钙、镁含量越高。"
				,"要少吃盐，这是关键。因为，食盐越多，血压升高的机率越大。要适量增加含每天食盐不应超过6克。，因为体内缺钙容易导致高血压，缺镁则易使血管变得硬化，所以要适量增加富含钾、钙、镁元素的食物。含镁高的食物有豆制品、牛奶、蚌壳、贝类食物。富含钙质的食物比较多，如海带、芝麻酱、山楂、虾米皮、绿叶蔬菜、雪里红、榨菜、鱼类、排骨和猪手等等。"
				,"要增加含维生素和矿物质的食物摄入，如水果、蔬菜和瘦肉等。这些食品中含有钾、维生素C、亚油酸等，它们能扩张血管，有利血管变得柔软，降低交感神经的活性，使血压降低，运转正常。适当增加不饱和脂肪酸的摄入，要减少动物脂肪的摄入，少吃含糖类食物，食糖过多则易升高甘油三酯，使血液粘度增大，并使血钙降低，不利于降低血压。应当戒烟戒酒。不能再吸烟饮酒，适当运动如打太极，散步养花等。"
				,"血压偏高的人饮食需要适时定量，不饥不饱，不暴饮暴食。食盐摄取每天应该限制在5克以下。浮肿明显时，更应该严格控制食盐。但长期低盐或者缺盐，可导致食欲不振，全身乏力等现象，所以不能无盐。血压偏高的人要多吃些含钾丰富的食物。如油菜、菠菜、小白菜及西红柿等。总之，血压高者的饮食应以清淡为主，宁素勿荤，宁淡勿浓，宁饥勿饱，生活上做到调情志，益肾精，慎饮食。"
				,"运动对高血压的重要性：有氧运动同减肥一样可以降低血压，如散步、慢跑、太极拳、骑自行车和游泳都是有氧运动。进行运动要注意：① 勿过量或太强太累，② 注意周围环境气候：夏天：避免中午艳阳高照的时间;冬天：要注意保暖 ③ 穿着舒适吸汗的衣服：④ 选择安全场所：如公园、学校，勿在巷道、马路边。⑤ 进行运动应在饭后2小时，切勿空腹，以免发生低血糖。同时注意饮食调节多吃水果蔬菜，清淡少盐低脂饮食。"
				,"血压正常高值根据我国流行病学统计血压数值120-139/80-89mmHg,十年后心血管发生危险度比110/75mmHg高1倍以上，患高血压概率增加45%~64%。可限制食盐摄入、多食绿叶蔬果和脱脂牛奶补充钙和钾盐、减少酒精摄入量、减少饱和脂肪摄入量和脂肪总量少吃肥肉，心脏和动物肝脏。减轻精神压力，保持心理平衡多听歌，多和朋友交流。减轻体重、增加运动例如散步，养花等。定期检测血压，以免发展成高血压。"
				,"临界高血压边缘型高血压 应进行科学的适量的锻炼， 以太极拳、步行、慢跑、保健气功（放松功）、医疗保健体操（如降压静心操）等比较合适；合理的饮食，良好的休息时间及乐观的心情。临界高血压患者的锻炼内容以放松性和耐力性练习为主。 减轻体重，限制食盐的摄入量，并且定期监测血压，必要时就医。"
				,"体检时发现为临界高血压者，需连续每日两次测血压共一周，观察血压变化;若一年之后，血压均值升高10毫米汞柱，就需要用药物治疗了。平时生活中可注意以下几点一是控制体重、减少脂肪摄入，约有80%的临界高血压者可通过减肥使血压降至正常;二是戒烟限酒，这是控制临界高血压发展的重要措施;三是限制盐、糖摄入，多数患者可通过这一措施使血压得到控制;四是增加钙、钾的摄入，如多吃虾皮、牛奶、鱼类、豆类、绿叶蔬菜、桔子、香蕉等食物;五是坚持运动，进行体育锻炼是临界高血压者降压的有效手段，患者宜长期坚持慢跑、快走、体操、太极拳等运动;六是保持心理平衡、防止情绪失控，有助于高血压恢复正常。"
				,"盐的摄入量与高血压呈正相关，即人群中盐摄入越多血压水平就越高。世界卫生组织规定每人每天摄盐量不得超过6克，这里的6克不仅指食盐，还包括味精、酱油等含盐调料和食品中的盐量。除听从医嘱服用适当的药物外，还要注意劳逸结合、注意饮食、适当运动、保持情绪稳定、睡眠充足。老年人降压不能操之过急，血压宜控制在140～159mmhg为宜，减少心脑血管并发症的发生。"
		});
	}
	public static String formaterHealthValue(float healthValue) {
		if(healthValue <= 0) {
			return "-";
		}
		return healthValue + "";
	}

	/**
	 * 心率结果
	 * @param heartRate
	 * @return
	 */
	public static String getHeartRateResult(float heartRate) {
		//60-100
		if(heartRate <= 0) {
			return HealthResult.UNKNOW.getName();
		}
		if(heartRate < 60) {
			return HealthResult.LOW.getName();
		} else if(heartRate > 100) {
			return HealthResult.HIGH.getName();
		}
		return HealthResult.IS_OK.getName();
	}
	
	/**
	 * 心率结果分析
	 * @param heartRate
	 * @return
	 */
	public static String getHeartRateResultAnalyze(float heartRate) {
		//60-100
		if(heartRate <= 0) {
			return "";
		}
		if(heartRate < 60) {
			return random(resultAnalyze.get("HEART_RATE_RESULT_ANALYZE_LOW"));
		} else if(heartRate > 100) {
			return random(resultAnalyze.get("HEART_RATE_RESULT_ANALYZE_HIGH"));
		}
		return random(resultAnalyze.get("HEART_RATE_RESULT_ANALYZE_IS_OK"));
	}
	
	/**
	 * 心率结果小贴士
	 * @param heartRate
	 * @return
	 */
	public static String getHeartRateResultTips(float heartRate) {
		//60-100
		if(heartRate <= 0) {
			return "";
		}
		if(heartRate < 60) {
			return random(tips.get("HEART_RATE_RESULT_TIPS_LOW"));
		} else if(heartRate > 100) {
			return random(tips.get("HEART_RATE_RESULT_TIPS_HIGH"));
		}
		return "";
	}
	
	/**
	 * 呼吸率结果
	 * @param respiratoryRate
	 * @return
	 */
	public static String getRespiratoryRateResult(float respiratoryRate) {
		//12-20
		if(respiratoryRate <= 0) {
			return HealthResult.UNKNOW.getName();
		}
		if(respiratoryRate < 12) {
			return HealthResult.LOW.getName();
		} else if(respiratoryRate > 20) {
			return HealthResult.HIGH.getName();
		}
		return HealthResult.IS_OK.getName();
	}
	/**
	 * 呼吸率分析结果
	 * @param respiratoryRate
	 * @return
	 */
	public static String getRespiratoryRateResultAnalyze(float respiratoryRate) {
		//12-20
		if(respiratoryRate <= 0) {
			return "";
		}
		return random(resultAnalyze.get("RESPIRATORY_RATE_RESULT_TIPS"));
	}
	/**
	 * 呼吸率结果小贴士
	 * @param respiratoryRate
	 * @return
	 */
	public static String getRespiratoryRateResultTips(float respiratoryRate) {
		//12-20
		if(respiratoryRate <= 0) {
			return "";
		}
		if(respiratoryRate < 12) {
			return random(tips.get("RESPIRATORY_RATE_RESULT_ANALYZE_LOW"));
		} else if(respiratoryRate > 20) {
			return random(tips.get("RESPIRATORY_RATE_RESULT_ANALYZE_HIGH"));
		}
		return random(tips.get("RESPIRATORY_RATE_RESULT_ANALYZE_IS_OK"));
	}
	
	/**
	 * 血氧结果
	 * @param bloodOxygen
	 * @return
	 */
	public static String getBloodOxygenResult(float bloodOxygen) {
		//90-100
		if(bloodOxygen <= 0) {
			return HealthResult.UNKNOW.getName();
		}
		if(bloodOxygen < 90) {
			return HealthResult.LOW.getName();
		} else if(bloodOxygen > 100) {
			return HealthResult.HIGH.getName();
		}
		return HealthResult.IS_OK.getName();
	}
	
	/**
	 * 血氧结果分析
	 * @param bloodOxygen
	 * @return
	 */
	public static String getBloodOxygenResultAnalyze(float bloodOxygen) {
		//90-100
		if(bloodOxygen <= 0) {
			return "";
		}
		if(bloodOxygen < 90) {
			return random(resultAnalyze.get("BLOOD_OXYGEN_RESULT_ANALYZE_LOW"));
		} else if(bloodOxygen > 100) {
			return random(resultAnalyze.get("BLOOD_OXYGEN_RESULT_ANALYZE_HIGH"));
		}
		return random(resultAnalyze.get("BLOOD_OXYGEN_RESULT_ANALYZE_IS_OK"));
	}
	
	/**
	 * 血氧结果小贴士
	 * @param bloodOxygen
	 * @return
	 */
	public static String getBloodOxygenResultTips(float bloodOxygen) {
		//90-100
		if(bloodOxygen <= 0) {
			return "";
		}
		return random(tips.get("BLOOD_OXYGEN_RESULT_TIPS_LOW"));
	}
	
	/**
	 * 脉率结果
	 * @param pulseRate
	 * @return
	 */
	public static String getPulseRateResult(float pulseRate) {
		//60-100
		if(pulseRate <= 0) {
			return HealthResult.UNKNOW.getName();
		}
		if(pulseRate < 60) {
			return HealthResult.LOW.getName();
		} else if(pulseRate > 100) {
			return HealthResult.HIGH.getName();
		}
		return HealthResult.IS_OK.getName();
	}
	/**
	 * 脉率结果分析
	 * @param pulseRate
	 * @return
	 */
	public static String getPulseRateResultAnalyze(float pulseRate) {
		//60-100
		if(pulseRate <= 0) {
			return "";
		}
		if(pulseRate < 60) {
			return random(resultAnalyze.get("PULSE_RATE_RESULT_ANALYZE_LOW"));
		} else if(pulseRate > 100) {
			return random(resultAnalyze.get("PULSE_RATE_RESULT_ANALYZE_HIGH"));
		}
		return random(resultAnalyze.get("PULSE_RATE_RESULT_ANALYZE_IS_OK"));
	}
	
	/**
	 * 收缩压结果
	 * @param systolicPressure
	 * @return
	 */
	public static String getSystolicPressureResult(float systolicPressure) {
		//90-139
		if(systolicPressure <= 0) {
			return HealthResult.UNKNOW.getName();
		}
		if(systolicPressure < 90) {
			return HealthResult.LOW.getName();
		} else if(systolicPressure > 139) {
			return HealthResult.HIGH.getName();
		}
		return HealthResult.IS_OK.getName();
	}
	/**
	 * 收缩压结果分析
	 * @param systolicPressure
	 * @return
	 */
	public static String getSystolicPressureResultAnalyze(float systolicPressure) {
		//90-139
		if(systolicPressure <= 0) {
			return "";
		}
		if(systolicPressure < 90) {
			return random(resultAnalyze.get("SYSTOLIC_PRESSURE_RESULT_ANALYZE_LOW"));
		} else if(systolicPressure > 139) {
			return random(resultAnalyze.get("SYSTOLIC_PRESSURE_RESULT_ANALYZE_HIGH"));
		}
		return random(resultAnalyze.get("SYSTOLIC_PRESSURE_RESULT_ANALYZE_IS_OK"));
	}
	
	/**
	 * 收缩压结果小贴士
	 * @param systolicPressure
	 * @return
	 */
	public static String getSystolicPressureResultTips(float systolicPressure) {
		//90-139
		if(systolicPressure <= 0) {
			return "";
		}
		if(systolicPressure < 90) {
			return random(tips.get("SYSTOLIC_PRESSURE_RESULT_TIPS_LOW"));
		} else if(systolicPressure > 139) {
			return random(tips.get("SYSTOLIC_PRESSURE_RESULT_TIPS_HIGH"));
		}
		return "";
	}
	
	/**
	 * 舒张压结果
	 * @param diastolicPressure
	 * @return
	 */
	public static String getDiastolicPressureResult(float diastolicPressure) {
		//60-89
		if(diastolicPressure <= 0) {
			return HealthResult.UNKNOW.getName();
		}
		if(diastolicPressure < 60) {
			return HealthResult.LOW.getName();
		} else if(diastolicPressure > 89) {
			return HealthResult.HIGH.getName();
		}
		return HealthResult.IS_OK.getName();
	}
	/**
	 * 舒张压结果分析
	 * @param diastolicPressure
	 * @return
	 */
	public static String getDiastolicPressureResultAnalyze(float diastolicPressure) {
		//60-89
		if(diastolicPressure <= 0) {
			return "";
		}
		if(diastolicPressure < 60) {
			return random(resultAnalyze.get("DIASTOLIC_PRESSURE_RESULT_ANALYZE_LOW"));
		} else if(diastolicPressure > 89) {
			return random(resultAnalyze.get("DIASTOLIC_PRESSURE_RESULT_ANALYZE_HIGH"));
		}
		return random(resultAnalyze.get("DIASTOLIC_PRESSURE_RESULT_ANALYZE_IS_OK"));
	}
	/**
	 * 舒张压结果小贴士
	 * @param diastolicPressure
	 * @return
	 */
	public static String getDiastolicPressureResultTips(float diastolicPressure) {
		//60-89
		if(diastolicPressure <= 0) {
			return "";
		}
		if(diastolicPressure < 60) {
			return random(tips.get("DIASTOLIC_PRESSURE_RESULT_TIPS_LOW"));
		} else if(diastolicPressure > 89) {
			return random(tips.get("DIASTOLIC_PRESSURE_RESULT_TIPS_HIGH"));
		}
		return "";
	}
	
	
	private static String random(String[] strings) {
		if(null == strings || strings.length <= 0) {
			return "";
		}
		int randomNumber = new Random().nextInt(strings.length);
		return strings[randomNumber];
	}
	
}
