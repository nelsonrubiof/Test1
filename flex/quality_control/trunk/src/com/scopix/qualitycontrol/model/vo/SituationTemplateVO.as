package com.scopix.qualitycontrol.model.vo
{
    import com.scopix.periscope.webservices.businessservices.SituationTemplateDTO;
    
    [Bindable]
    public class SituationTemplateVO
    {
        public var id:Number;
        public var name:String;
        
        public function SituationTemplateVO()
        {
        }
        
        public function fromDTO(dto:SituationTemplateDTO):void {
            this.name = dto.name;
            this.id = dto.id;
        }
        
        public function toDTO():SituationTemplateDTO {
            var dto:SituationTemplateDTO = new SituationTemplateDTO();
            
            dto.name = this.name;
            dto.id = this.id;
            
            return dto;
        }
        
        public function clone():SituationTemplateVO {
            var stVO:SituationTemplateVO = new SituationTemplateVO();
            
            stVO.id = this.id;
            stVO.name = this.name;
            
            return stVO;
        }
    }
}